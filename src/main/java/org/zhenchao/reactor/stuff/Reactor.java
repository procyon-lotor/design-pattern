package org.zhenchao.reactor.stuff;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * http://jeewanthad.blogspot.hk/2013/02/reactor-pattern-explained-part-1.html
 *
 * @author zhenchao.wang 2017-01-18 22:35
 * @version 1.0.0
 */
public class Reactor implements Runnable {

    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;
    private final boolean useThreadPool;

    public Reactor(int port, boolean useThreadPool) throws IOException {
        this.useThreadPool = useThreadPool;
        this.selector = Selector.open();
        this.serverSocketChannel = ServerSocketChannel.open();
        this.serverSocketChannel.socket().bind(new InetSocketAddress(port));
        this.serverSocketChannel.configureBlocking(false);
        SelectionKey selectionKey = this.serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        selectionKey.attach(new Acceptor());
    }

    @Override
    public void run() {
        System.out.println("Server listening on port: " + serverSocketChannel.socket().getLocalPort());
        try {
            while (!Thread.interrupted()) {
                System.out.println("Thread-" + Thread.currentThread().getId() + " is waiting...");
                selector.select();
                Set selected = selector.selectedKeys();
                Iterator itr = selected.iterator();
                while (itr.hasNext()) {
                    this.dispatch((SelectionKey) (itr.next()));
                }
                selected.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dispatch(SelectionKey key) {
        Runnable acceptor = (Runnable) (key.attachment());
        if (acceptor != null) {
            // 调用Accepter的run方法
            acceptor.run();
        }
    }

    private class Acceptor implements Runnable {
        public void run() {
            try {
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    if (useThreadPool) {
                        new HandlerWithThreadPool(selector, socketChannel);
                    } else {
                        new HandlerWithoutThreadPool(selector, socketChannel);
                    }
                }
                System.out.println("Connection accepted by reactor!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new Thread(new Reactor(8080, false)).start();
    }

}

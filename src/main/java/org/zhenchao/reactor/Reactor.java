package org.zhenchao.reactor;

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

    public Reactor(int port) throws IOException {
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
                // System.out.println("Thread-" + Thread.currentThread().getId() + " is waiting...");
                selector.select();
                Set selected = selector.selectedKeys();
                Iterator itr = selected.iterator();
                while (itr.hasNext()) {
                    this.dispatch((SelectionKey) (itr.next()));
                }
                selected.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dispatch(SelectionKey key) throws Exception {
        Runnable acceptor = (Runnable) (key.attachment());
        if (acceptor != null) acceptor.run();
    }

    private class Acceptor implements Runnable {

        @Override
        public void run() {
            try {
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) new Handler(selector, socketChannel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

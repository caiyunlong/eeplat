package com.exedosoft.plat.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Basket {

	Lock lock = new ReentrantLock();

	// ����Condition����

	Condition produced = lock.newCondition();

	Condition consumed = lock.newCondition();

	boolean available = false;

	public void produce() throws InterruptedException {
		
		consumed.await(); 

		lock.lock();

		try {

			if (available) {

				consumed.await(); // ����lock����˯��

			}

			/* ����ƻ�� */

			System.out.println("Apple produced.");

			available = true;

			produced.signal(); // ���źŻ��ѵȴ����Condition���߳�
	

		} finally {

			lock.unlock();

		}

	}

	public void consume() throws InterruptedException {

		lock.lock();

		try {

			if (!available) {

				produced.await();// ����lock����˯��

			}

			/* ��ƻ�� */

			System.out.println("Apple consumed.");

			available = false;

			consumed.signal();// ���źŻ��ѵȴ����Condition���߳�

		} finally {

			lock.unlock();

		}

	}

	public static void main(String[] args) throws InterruptedException {

		final Basket basket = new Basket();

		// ����һ��producer

		Runnable producer = new Runnable() {

			public void run() {

				try {

					basket.produce();

				} catch (InterruptedException ex) {

					ex.printStackTrace();

				}

			}

		};

		// ����һ��consumer

		Runnable consumer = new Runnable() {

			public void run() {

				try {

					basket.consume();

				} catch (InterruptedException ex) {

					ex.printStackTrace();

				}

			}

		};

		// ������10��consumer��producer

		ExecutorService service = Executors.newCachedThreadPool();
		service.submit(producer);
		service.submit(producer);
		

//
//		for (int i = 0; i < 10; i++)
//
//			service.submit(consumer);
//
//		Thread.sleep(2000);
//
//		for (int i = 0; i < 10; i++)
//
//			service.submit(producer);
//
//		service.shutdown();

	}

}

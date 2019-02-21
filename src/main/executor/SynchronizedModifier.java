package main.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import main.task.SynchronizedTask;

/**
 * <p>同期処理の実験クラス.
 * {@code synchronized}を用いた同期処理を実現する.
 * 
 * @author Naoto Wada
 *
 */
public class SynchronizedModifier {

	/**
	 * main関数
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("synchronized修飾子");

		ExecutorService exec = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 20; i++) {
			Runnable task = new SynchronizedTask(String.valueOf(i));
			exec.execute(task);
		}
		exec.shutdown();

		// タスクが実行されるまでいったん待つ
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("実行想定順序 " + SynchronizedTask.getQueueList());
		System.out.println("実行結果順序 " + SynchronizedTask.getExecutedList());
	}
}

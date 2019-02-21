package main.task;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * <p>実行タスククラス.
 * 各タスクごとに一意の名前を持っており、フィールドを読み書きする.
 */
public class SynchronizedTask implements Runnable {

	private static final List<String> WAITING_LIST = new CopyOnWriteArrayList<>();
	private static final List<String> EXECUTED_LIST = new CopyOnWriteArrayList<>();

	private String NAME;

	public SynchronizedTask(String name) {
		this.NAME = name;
	}

	@Override
	public void run() {
		// 同期実行される
		actionSync(this.NAME);
	}

	/**
	 * 実行タスク名の出力処理.
	 * {@code synchronized}なので同期処理だが、順序は保証しない
	 * @param name
	 */
	private void actionSync(String name) {
		WAITING_LIST.add(name);
		add(name);

		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private synchronized void add(String name) {
		EXECUTED_LIST.add(name);
	}

	public static List<String> getQueueList(){
		return WAITING_LIST;
	}
	public static List<String> getExecutedList(){
		return EXECUTED_LIST;
	}
}

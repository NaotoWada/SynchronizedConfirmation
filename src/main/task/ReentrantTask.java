package main.task;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>実行タスククラス.
 * 各タスクごとに一意の名前を持っており、フィールドを読み書きする.
 */
public class ReentrantTask implements Runnable {

	private final ReentrantLock LOCK = new ReentrantLock(true);
	
	private static final List<String> WAITING_LIST = new CopyOnWriteArrayList<>();
	private static final List<String> EXECUTED_LIST = new CopyOnWriteArrayList<>();

	private String NAME;

	public ReentrantTask(String name) {
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
		try {
			LOCK.lock();
			add(name);
		} finally {
			LOCK.unlock();
		}

		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void add(String name) {
		EXECUTED_LIST.add(name);
	}

	public static List<String> getQueueList(){
		return WAITING_LIST;
	}
	public static List<String> getExecutedList(){
		return EXECUTED_LIST;
	}
}

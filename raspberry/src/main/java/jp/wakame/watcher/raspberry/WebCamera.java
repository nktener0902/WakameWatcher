package jp.wakame.watcher.raspberry;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WebCamera {

	private String photoDir;

	public void setPhotoDirPath(String dir) {
		photoDir = dir;
	}

	public void takePhoto() throws IOException {
		/** 現在時刻を取得 **/
		Date date = new Date();
		String currentData = date.toString();

		/** ファイル名設定 **/
		String filename = photoDir + currentData + ".jpg";

		/** 写真を撮る **/
		Process process = new ProcessBuilder("fswebcam", "--no-banner", "-r", "1920x1080", "-D", "5", filename).start();
	}

	public boolean oldPhotoRemove() throws IOException {
		/** ディレクトリ内の画像数を取得 **/
		File dir = new File(photoDir);
		File[] files = dir.listFiles();
		int fileAmount = files.length;
		boolean removed = false;

		/** ファイル数が100より多い場合、最も古い写真を削除 **/
		if (fileAmount > 100) {
			removed = true;
			Process process = new ProcessBuilder("rm", photoDir + files[0]).start();
		}
		return removed;
	}
}

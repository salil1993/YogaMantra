package com.deftminds.coronavirusapp.util;

public class LocalEventFromMainActivity {

	public static class StartPlayback {

	}

	public static class ResetPlayback {

	}

	public static class PausePlayback {

	}
	public static class StopPlayback {

	}

	public static class StopUpdatingSeekbarWithMediaPosition {

	}

	public static class StartUpdatingSeekbarWithPlaybackPosition {

	}

	public static class SeekTo {

		public final int position;

		public SeekTo(int position) {
			this.position = position;
		}
	}

}
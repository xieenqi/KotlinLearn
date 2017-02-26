package kotlin11

import android.media.AudioTrack
import kotlin.properties.Delegates

/**
 * Created by qiqi on 17/2/26.
 */
class Player {

    //Delegates.observable 捕获State的成员状态 传给后面的lamda 函数
    private var state: State by Delegates.observable(State.IDLE, { pro, olde, new ->
        println("$olde -> $new")
        onPlayStateChangeListenter?.onStateChange(olde, new)//?. 表示必定不为空 (安全调用方法)
    })

    private fun sendCmd(cmd: PlayerCmd) {
        when (cmd) {
            is PlayerCmd.Play -> {
                println("\n播放地址: ${cmd.url} 播放开始时间: ${cmd.postin}ms")
                state = State.PLAYING
                doPlay(cmd.url, cmd.postin)
            }
            is PlayerCmd.Resume -> {
                println("\n 继续播放")
                state = State.PLAYING
                doResume()
            }
            is PlayerCmd.Pause -> {
                println("\n 暂停播放")
                state = State.PAUSED
                doPause()
            }
            is PlayerCmd.Stop -> {
                println("\n 停止播放")
                state = State.IDLE
                doStop()
            }
            is PlayerCmd.Seek -> {
                println("\n 继续播放: ${cmd.postin}ms , 状态: ${state}")
                state = State.PAUSED
                doPause()
            }
        }
    }

    private fun doPlay(url: String, postion: Long) {

    }

    private fun doResume() {

    }

    private fun doPause() {

    }

    private fun doStop() {

    }

    interface OnPlayStateChangeListenter {
        fun onStateChange(OldeState: State, newState: State)
    }

    var onPlayStateChangeListenter: OnPlayStateChangeListenter? = null

    fun play(url: String, postion: Long = 0) {//播放位置初始位置为0
        sendCmd(PlayerCmd.Play(url, postion))
    }

    fun resume() {
        sendCmd(PlayerCmd.Resume)
    }

    fun pause() {
        sendCmd(PlayerCmd.Pause)
    }

    fun stop() {
        sendCmd(PlayerCmd.Stop)
    }

    fun seekTo(postion: Long) {
        sendCmd(PlayerCmd.Seek(postion))
    }
}
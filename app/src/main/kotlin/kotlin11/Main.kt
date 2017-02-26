package kotlin11

/**
 * Created by qiqi on 17/2/26.
 */

fun main(args: Array<String>) {
    var play: Player = Player()
    play.play("www.baidu.com")

    play.pause()

    play.stop()

    play.resume()

    play.seekTo(500)

}
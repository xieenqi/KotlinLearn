import rx.Observable
import java.util.concurrent.Executors


/**
 * Created by qiqi on 17/1/23.
 */
// compile 'io.reactivex:rxjava:1.2.1'  rxjava 依赖
fun main(vararg a: String) {
    val text = "import android.content.Context" +
            "import android.content.pm.PackageManager" +
            "import android.media.AudioManager" +
            "import android.media.MediaPlayer" +
            "import android.media.MediaRecorder" +
            "import android.media.SoundPool" +
            "import android.os.Environment;" +
            "import android.os.Handler;"
    Observable.from(text.toCharArray().asIterable()).
            filter { !it.isWhitespace() }//过滤空格
            .groupBy { it }//分组
            .map {
                o ->
                o.count().subscribe {
                    println("${o.key} -> $it ")
                }
            }.subscribe()
    val work=Executors.newWorkStealingPool()
    work.execute{
        println("hello")
    }


}



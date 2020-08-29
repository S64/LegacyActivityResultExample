package jp.s64.android.example.legacyactivityresult.x

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.entrypoint.*
import kotlinx.android.synthetic.main.launcher.*
import kotlinx.android.synthetic.main.target.*

private fun msg(context: Context, tag: String, msg: String) {
    Toast.makeText(context, "[${tag}] ${msg}", Toast.LENGTH_SHORT).show()
    Log.d(tag, msg)
}

class PlatformEntrypoint : android.app.Activity() {

    companion object {

        private const val TAG = "PlatformEntrypoint"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.entrypoint)

        fragmentManager.beginTransaction()
            .replace(platformContainer.id, PlatformLauncher())
            .commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        msg(this, TAG, "onActivityResult(${requestCode}/${resultCode})")
    }

}

class SupportEntrypoint : androidx.appcompat.app.AppCompatActivity() {

    companion object {

        private const val TAG = "SupportEntrypoint"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.entrypoint)

        fragmentManager.beginTransaction()
            .replace(platformContainer.id, PlatformLauncher())
            .commit()

        supportFragmentManager.beginTransaction()
            .replace(supportContainer.id, SupportLauncher())
            .commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        msg(this, TAG, "onActivityResult(${requestCode}/${resultCode})")
    }

}


class PlatformLauncher : android.app.Fragment() {

    companion object {

        private const val TAG = "PlatformLauncher"

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.launcher, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentType.text = "Platform"
        launchTarget.setOnClickListener {
            startActivityForResult(Target.newIntent(activity), 3001)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        msg(activity, TAG, "onActivityResult(${requestCode}/${resultCode})")
    }

}

class SupportLauncher : androidx.fragment.app.Fragment() {

    companion object {

        private const val TAG = "SupportLauncher"

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.launcher, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentType.text = "Support"
        launchTarget.setOnClickListener {
            startActivityForResult(Target.newIntent(requireContext()), 3002)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        msg(requireContext(), TAG, "onActivityResult(${requestCode}/${resultCode})")
    }

}

class Target : androidx.appcompat.app.AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, Target::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.target)

        finishWithOk.setOnClickListener {
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        }

        finishWithCancel.setOnClickListener {
            setResult(AppCompatActivity.RESULT_CANCELED)
            finish()
        }

        finishWithNothing.setOnClickListener {
            finish()
        }
    }

}

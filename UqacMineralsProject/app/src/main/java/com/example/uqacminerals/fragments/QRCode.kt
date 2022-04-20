
    package com.example.uqacminerals.fragments

    import android.os.Bundle
    import android.util.Log
    import android.view.*
    import androidx.fragment.app.Fragment
    import android.widget.Toast
    import androidx.core.os.bundleOf
    import androidx.fragment.app.setFragmentResult
    import com.budiyev.android.codescanner.CodeScanner
    import com.budiyev.android.codescanner.CodeScannerView
    import com.budiyev.android.codescanner.DecodeCallback
    import com.example.uqacminerals.R
    import com.example.uqacminerals.ui.main.MainActivity
    import kotlin.math.abs


    class QRCode : Fragment(), GestureDetector.OnGestureListener {

        // Declaring gesture detector
        private lateinit var gestureDetector: GestureDetector

        private lateinit var codeScanner: CodeScanner

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {



            return inflater.inflate(R.layout.fragment_qr_code, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

            val scannerView = view.findViewById<CodeScannerView>(R.id.scanner_view)
            val activity = requireActivity()
            codeScanner = CodeScanner(activity, scannerView)
            codeScanner.decodeCallback = DecodeCallback {
                activity.runOnUiThread {
                    Toast.makeText(activity, it.text, Toast.LENGTH_LONG).show()
                    // passer le bon fragmentresult correspondant au piédestal
                    //https://developer.android.com/guide/fragments/communicate
                    setFragmentResult("Groupe", bundleOf("GroupeKey" to 1))

                    (getActivity() as MainActivity).ChangeFragment("QRCode","ScannedMineralList")

                }
            }
            scannerView.setOnClickListener {
                codeScanner.startPreview()
            }

            // Initializing the gesture
            gestureDetector = GestureDetector(this)

            // this is the view we will add the gesture detector to
            val gestureView : View = requireView().findViewById(R.id.scanner_view)

            gestureView.setOnTouchListener(object : View.OnTouchListener {

                override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                    return gestureDetector.onTouchEvent(p1)
                }

            })

        }

        override fun onResume() {
            super.onResume()
            codeScanner.startPreview()
        }

        override fun onPause() {
            codeScanner.releaseResources()
            super.onPause()
        }




        override fun onDown(p0: MotionEvent): Boolean {
            return true
        }
        override fun onShowPress(p0: MotionEvent) {
            return
        }
        override fun onSingleTapUp(p0: MotionEvent): Boolean {
            return false
        }
        override fun onScroll(p0: MotionEvent, p1: MotionEvent, p2: Float, p3: Float): Boolean {
            return false
        }
        override fun onLongPress(p0: MotionEvent) {
            return
        }
        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            val swipeThreshold = 100
            val swipeVelocityThreshold = 100
            try {
                val diffY = e2.y - e1.y
                val diffX = e2.x - e1.x
                if (abs(diffX) > abs(diffY)) {
                    if (abs(diffX) > swipeThreshold && abs(velocityX) > swipeVelocityThreshold) {
                        if (diffX > 0) {
                            //Toast.makeText(context, "Left to Right swipe gesture", Toast.LENGTH_SHORT).show()
                            Log.e("SWIPE","swiping left to right")
                        }
                        else {
                            (activity as MainActivity).ChangeFragment("QRCode","Wiki")
                            Log.e("SWIPE","swiping right to left")
                        }
                    }
                }
            }
            catch (exception: Exception) {
                exception.printStackTrace()
            }
            return true
        }
    }


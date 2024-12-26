package com.example.arskripsi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.PlacementMode
import io.github.sceneview.math.Position
import android.util.Log
import androidx.core.view.isGone
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.ar.core.Config

class LihatAR : AppCompatActivity() {

    private lateinit var sceneView: ArSceneView
    private lateinit var placeButton: FloatingActionButton
    private lateinit var modelNode: ArModelNode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ar)

        try {
            sceneView = findViewById<ArSceneView>(R.id.sceneView).apply {
                this.lightEstimationMode = Config.LightEstimationMode.DISABLED
            }
            placeButton = findViewById(R.id.place)
            placeButton.setOnClickListener {
                placeModel()
            }

            val modelLocation = intent.getStringExtra("MODEL_LOCATION") ?: "assets/models/meja.glb"
            Log.d("LihatARbang", "Model location: $modelLocation")

            modelNode = ArModelNode(sceneView.engine, PlacementMode.INSTANT).apply {
                loadModelGlbAsync(
                    glbFileLocation = modelLocation,
                    scaleToUnits = 0.7f,
                    centerOrigin = Position(-0.5f)
                ) {
                    sceneView.planeRenderer.isVisible = false
                }
                onAnchorChanged = {
                    placeButton.isGone = it != null
                }
            }
            sceneView.addChild(modelNode)
        } catch (e: Exception) {
            Log.e("LihatAR", "Error initializing AR", e)
        }
    }

    private fun placeModel() {
        try {
            modelNode.anchor()
            sceneView.planeRenderer.isVisible = false
        } catch (e: Exception) {
            Log.e("LihatAR", "Error placing model", e)
        }
    }
}
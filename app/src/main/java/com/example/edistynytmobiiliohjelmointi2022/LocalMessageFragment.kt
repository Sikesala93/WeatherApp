package com.example.edistynytmobiiliohjelmointi2022

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.edistynytmobiiliohjelmointi2022.databinding.FragmentDataReadBinding
import com.example.edistynytmobiiliohjelmointi2022.databinding.FragmentLocalMessageBinding
import com.example.edistynytmobiiliohjelmointi2022.databinding.FragmentMqttBinding
import com.example.edistynytmobiiliohjelmointi2022.datatypes.weatherstation.WeatherStation
import com.google.gson.GsonBuilder
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

class LocalMessageFragment : Fragment() {
    // change this to match your fragment name
    private var _binding: FragmentLocalMessageBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    var mqttAndroidClient: MqttAndroidClient? = null

    // use tcp://10.0.2.2:1883 if using local mosquitto broker
    val serverUri = "tcp://10.0.2.2:1883"
    // USE A UNIQUE CLIENT-ID. replace the word "tvtplab1234" with your own identifier
    // for example, your student code
    var clientId = BuildConfig.MQTT_CLIENT
    val subscriptionTopic = "test/topic"

    // username and password not needed if using local mosquitto broker
    val mqttUsername = "developer"
    val mqttPassword = "Password123"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocalMessageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.buttonSendMessage.setOnClickListener {
            Log.d("ADVTECH", "VIESTI LÄHTEE")
            val text: String = binding.editTextNewMessage.text.toString()

            val message : MqttMessage = MqttMessage()
            message.payload = text.toByteArray()
            message.qos = 0

            mqttAndroidClient?.publish(subscriptionTopic, message)
        }


        // Create the client!
        mqttAndroidClient = MqttAndroidClient(context, serverUri, clientId)
        // CALLBACKS, these will take care of the connection if something unexpected happen
        mqttAndroidClient?.setCallback(object : MqttCallbackExtended {
            override fun connectComplete(reconnect: Boolean, serverURI: String) {
                if (reconnect) {
                    Log.d("ADVTECH", "reconnected to MQTT")
                    // we have to subscribe again because of the reconnection
                    subscribeToTopic()
                } else {
                    Log.d("ADVTECH", "connected to MQTT")
                }
            }
            override fun connectionLost(cause: Throwable) {
                Log.d("ADVTECH", "MQTT connection lost")
            }
            @Throws(Exception::class)
            override fun messageArrived(topic: String, message: MqttMessage) {
                // THIS VARIABLE IS THE JSON DATA. you can use GSON to get the needed
                // data (temperature for example) out of it, and show it in a textview or something else
                try {
                    val result = String(message.payload)
                    Log.d("ADVTECH", result)
                    //binding.textViewTemperature.text = result

                    binding.textViewLocalMessage.text = result


                } catch (e: Exception) {
                    Log.d("ADVTECH", e.toString());
                }
            }
            // used when sending data via MQTT
            override fun deliveryComplete(token: IMqttDeliveryToken) {}
        })
        // CONNECT TO MQTT

        val mqttConnectOptions = MqttConnectOptions()
        mqttConnectOptions.isAutomaticReconnect = true
        mqttConnectOptions.isCleanSession = true
        mqttConnectOptions.userName = mqttUsername
        mqttConnectOptions.password = mqttPassword.toCharArray()
        try {
            mqttAndroidClient?.connect(mqttConnectOptions, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    val disconnectedBufferOptions = DisconnectedBufferOptions()
                    disconnectedBufferOptions.isBufferEnabled = true
                    disconnectedBufferOptions.bufferSize = 100
                    disconnectedBufferOptions.isPersistBuffer = false
                    disconnectedBufferOptions.isDeleteOldestMessages = false
                    mqttAndroidClient!!.setBufferOpts(disconnectedBufferOptions)
                    subscribeToTopic()
                }
                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                    Log.d("ADVTECH", "Failed to connect!")
                }
            })
        } catch (ex: MqttException) {
            ex.printStackTrace()
        }
        return root
    }
    // subscriber method
    fun subscribeToTopic() {
        try {
            mqttAndroidClient?.subscribe(subscriptionTopic, 0, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    Log.d("ADVTECH", "Subscribed!")
                }
                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                    Log.d("ADVTECH", "Failed to subscribe")
                }
            })
        } catch (ex: MqttException) {
            System.err.println("Exception whilst subscribing")
            ex.printStackTrace()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mqttAndroidClient?.close()
    }
}
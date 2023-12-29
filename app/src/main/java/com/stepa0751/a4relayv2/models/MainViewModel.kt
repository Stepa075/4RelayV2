package com.stepa0751.a4relayv2.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel(){

    val dataLiveData = MutableLiveData<DataModel>()
    val dataTransferWeb = MutableLiveData<TransferData>()
    val dataTransferLocal = MutableLiveData<TransferDataLocal>()
    val dataReceived = MutableLiveData<TransferReceivedData>()
}
package com.example.myapplication.util

class Constant1 {

    interface RESPONSE_CODE {
        companion object {
            const val RESPONSE_200 = 200
            const val RESPONSE_500 = 500
            const val RESPONSE_400 = 400
            const val RESPONSE_401 = 401
            const val IS_INTRO_COMPLETE="IS_INTRO_COMPLETE"
            const val IS_LOGIN = "IS_LOGIN"
            const val BOTTOM_NAV_BROADCAST_NAME="BOTTOM_NAV_BROADCAST_NAME"


        }
    }

    interface BRODCAST_RECIVER {
        companion object {
           const val SIDE_MENU = "SIDE_MENU"

        }
    }

    interface DO_NOT_SHOW_AGAIN {
        companion object {
            const val HOW_TO_MEASURE = "HOW_TO_MEASURE"

        }
    }
}
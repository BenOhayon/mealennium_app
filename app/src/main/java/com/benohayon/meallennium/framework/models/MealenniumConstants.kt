package com.benohayon.meallennium.framework.models

  ///////////
 // Paths //
///////////

const val FIREBASE_STORAGE_IMAGE_URL = "gs://meallennium-599e9.appspot.com"


  /////////////////////////
 //  Intent values keys //
/////////////////////////

const val TAKEN_PICTURE_URL_KEY = "takenPictureUri"

  ///////////////////////////
 // Keys for Storing data //
///////////////////////////

const val USER_FIRST_NAME_KEY = "firstName"
const val USER_LAST_NAME_KEY = "lastName"
const val USER_EMAIL_KEY = "email"
const val USER_LOGIN_METHOD_KEY = "loginMethod"

const val POST_TITLE_KEY = "postTitle"
const val POST_AUTHOR_KEY = "postAuthor"
const val POST_SUMMERY_KEY = "postSummery"
const val POST_CONTENT_KEY = "postContent"


  //////////////////////////
 // Animations durations //
//////////////////////////

const val POST_LIST_ACTIVITY_SEARCH_BOX_ANIMATION_DURATION = 200L

const val SPLASH_ACTIVITY_LOGO_IMAGE_FADE_ANIMATION_DURATION = 1000L
const val SPLASH_ACTIVITY_APP_TITLE_ZOOM_IN_ANIMATION_DURATION = 1000L
const val SPLASH_ACTIVITY_MOVE_TO_NEXT_SCREEN_DELAY = 1500L


  //////////////
 // Requests //
//////////////

const val IMAGE_CAPTURE_REQUEST = 1111
const val ASK_CAMERA_PERMISSION_REQUEST = 1112
const val CREATE_POST_REQUEST = 1113
const val PICK_IMAGE_FROM_GALLERY_REQUEST = 1114


  /////////////
 // Results //
/////////////

const val RESULT_FAIL = -22
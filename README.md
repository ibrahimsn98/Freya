<img src="https://github.com/ibrahimsn98/freya/blob/master/art/hero.png" />

A lightweight, simplified form validation library for Android

[![](https://jitpack.io/v/ibrahimsn98/freya.svg)](https://jitpack.io/#ibrahimsn98/freya)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)


## Screenshots


<img src="https://github.com/ibrahimsn98/freya/blob/master/art/screenshot.jpg" width="428" />


## Validation Rules Setup

```xml
<?xml version="1.0" encoding="utf-8" ?>
<resources xmlns:app="http://schemas.android.com/apk/res-auto">

    <formField
        app:id="@id/inputUsername"
        app:required="true"
        app:minSize="3"
        app:maxSize="25" />

    <formField
        app:id="@id/inputEmail"
        app:email="true" />

    <formField
        app:id="@id/inputPhone"
        app:required="true"
        app:phoneNumber="true" />

    <formField
        app:id="@id/inputPassword"
        app:required="true"
        app:minSize="3"
        app:maxSize="25" />

</resources>
```


## Form Layout Setup

```xml
<me.ibrahimsn.lib.FreyaForm
    android:id="@+id/freya"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:config="@xml/form_app">

    <com.google.android.material.textfield.TextInputLayout
        android:id="@+id/inputEmail"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginBottom="12dp">

        <com.google.android.material.textfield.TextInputEditText
            android:inputType="textEmailAddress"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Email Address"/>

    </com.google.android.material.textfield.TextInputLayout>

    <com.google.android.material.textfield.TextInputLayout
        android:id="@+id/inputPassword"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:passwordToggleEnabled="true"
        android:layout_marginBottom="16dp">

        <com.google.android.material.textfield.TextInputEditText
            android:inputType="textPassword"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Password"/>

    </com.google.android.material.textfield.TextInputLayout>

</me.ibrahimsn.lib.FreyaForm>
```


## Validation on Form Submit

```kotlin
val freya = findViewById<FreyaForm>(R.id.freya)
val submitButton = findViewById<Button>(R.id.submit)

/*
 * Listens if the whole form is valid
 */
freya.onValidationChangeListener = {
    Log.d(TAG, "Is form valid: $it")
}

/*
 * Listens for form validation errors after validate()
 */
freya.onErrorListener = {
    Log.d(TAG, "Validation errors: $it")
}

submitButton.setOnClickListener {
    Log.d(TAG, "Is form valid: ${freya.validate()} values: ${freya.values}")
}
```


## Realtime Field Validation

```kotlin
/*
 * Listens for validation changes of any form field
 */
freya.onFieldValidationChangeListener = {
    when (it.error) {
        is Ruler.Required -> {
            it.setError("This field is required.")
        }
        is Ruler.Email -> {
            it.setError("Please enter a valid email address.")
        }
        is Ruler.PhoneNumber -> {
            it.setError("Please enter a valid phone number.")
        }
        is Ruler.MinSize -> {
            it.setError("This field must contain at least ${it.error?.param} characters.")
        }
        is Ruler.MaxSize -> {
            it.setError("This field must contain at most ${it.error?.param} characters.")
        }
        is Ruler.Regex -> {
            it.setError("Please provide a valid data.")
        }
    }
}
```


## Get Values & Prefill Form

```kotlin
val values: Map<Int, Any?> = freya.values

freya.setup(
    mapOf(
        R.id.inputUsername to "Thomas"
    )
)

```


## Note

Currently supported field view types are `TextInputLayout`, `TextInputEditText`, `EditText`. 
Support for more view types and new validation rules will be added in the future.


## Setup

> Follow me on Twitter [@ibrahimsn98](https://twitter.com/ibrahimsn98)

Step 1. Add the JitPack repository to your build file
```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
Step 2. Add the dependency
```groovy
dependencies {
    implementation 'com.github.ibrahimsn98:freya:1.0.0'
}
```


## License
```
MIT License

Copyright (c) 2020 İbrahim Süren

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#https://issuetracker.google.com/issues/265188224

-keep,allowshrinking class * extends androidx.compose.ui.node.ModifierNodeElement {

}
-dontwarn com.google.firebase.perf.network.FirebasePerfUrlConnection
-dontwarn javax.annotation.processing.AbstractProcessor
-dontwarn javax.annotation.processing.SupportedOptions

-keepattributes Signature,RuntimeVisibleAnnotations,AnnotationDefault
-keepclassmembers class * {
    @kotlinx.serialization.SerialName *;
}
-keepclassmembers class * {
    @kotlinx.serialization.Serializable *;
}

-keepclassmembers enum * {
    @kotlinx.serialization.SerialName *;
}

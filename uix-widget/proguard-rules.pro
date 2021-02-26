# AgentWeb
-keep class com.just.agentweb.** {
    *;
}
-dontwarn com.just.agentweb.**
# Matisse
-dontwarn com.bumptech.glide.**

# Viewbinging
-keep class me.shouheng.uix.widget.databinding.** {
    public static * inflate(android.view.LayoutInflater);
}
## Nawt

A mod made to replace the applet rendering with a compatibility-friendly framework for any windowing library.  
This mod comes with glfw as a built-in default Window Manager, but custom backends can be written and used with entrypoints. 

This mod should work with almost any beta fabric mod since it touches mostly LWJGL classes and a 2 or 3 tiny redirects in Minecraft sources; 
however it is built with STAPI in mind and that's where most of the compatibility effort is going to go.

## Known Issues
Fullscreen while using sway: Seems to forget resize events when the window is in tiling mode and exiting fullscreen.  
This issue was only tested on sway, but this could occur on i3, any other tiling window manager, or possibly on any WM in general.

## Warning
This mod has not been tested on a variety of platforms, so it's a use-at-your-own-risk kind of thingp; however, it is very unlikely anything will happen as long as you're using a unix-like or Windows operating system.    

Feel free to make issues or pull requests if anything feels wrong is is broken

## Future

I could possibly make some bindings for glfw on the path or sdl2

## License

MIT

## Nawt

A mod made to replace the applet rendering with a framework for any windowing library, with built-in glfw as a default

This mod should work with almost any beta fabric mod since it touches mostly LWJGL classes and a 2 or 3 tiny redirects in Minecraft sources; however its built with STAPI in mind

## Known Issues
Fullscreen while using sway: Seems to forget resize events when the window is in tiling mode and exiting fullscreen  
This issue was only tested on sway, but this could occur on i3, any other tiling window manager, or possibly on any WM in general

## Warning

This mod is definitely highly-experimental, so it's a use-at-your-own-risk kind of thing, but it is very unlikely anything will happen  
Feel free to make issues or pull requests

## Future

I might look into entrypoints for loading new windowing systems, and I could possibly make some bindings for glfw on the path or sdl2

## License

MIT, ignore the LICENSE file that came with the project, ill fix that later

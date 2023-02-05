package io.jaquobia.nawt.impl.glfw;

import io.github.jaquobia.Glfw;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.lwjgl.input.Keyboard;

@Environment(EnvType.CLIENT)
public class LwjglToGlfwHelper {
    public static int translateKeyToGlfw(int key) {
        return LwjglToGlfw[key];
    }
    public static int translateKeyToLWJGL(int key) {
        return GlfwToLwjgl[key];
    }

    private static final int[] LwjglToGlfw;
    private static final int[] GlfwToLwjgl;

    static {
        LwjglToGlfw = new int[Glfw.GLFW_KEY_LAST + 1];
        LwjglToGlfw[Keyboard.KEY_ESCAPE] = Glfw.GLFW_KEY_ESCAPE;
        LwjglToGlfw[Keyboard.KEY_1] = Glfw.GLFW_KEY_1;
        LwjglToGlfw[Keyboard.KEY_2] = Glfw.GLFW_KEY_2;
        LwjglToGlfw[Keyboard.KEY_3] = Glfw.GLFW_KEY_3;
        LwjglToGlfw[Keyboard.KEY_4] = Glfw.GLFW_KEY_4;
        LwjglToGlfw[Keyboard.KEY_5] = Glfw.GLFW_KEY_5;
        LwjglToGlfw[Keyboard.KEY_6] = Glfw.GLFW_KEY_6;
        LwjglToGlfw[Keyboard.KEY_7] = Glfw.GLFW_KEY_7;
        LwjglToGlfw[Keyboard.KEY_8] = Glfw.GLFW_KEY_8;
        LwjglToGlfw[Keyboard.KEY_9] = Glfw.GLFW_KEY_9;
        LwjglToGlfw[Keyboard.KEY_0] = Glfw.GLFW_KEY_0;
        LwjglToGlfw[Keyboard.KEY_MINUS] = Glfw.GLFW_KEY_MINUS;
        LwjglToGlfw[Keyboard.KEY_EQUALS] = Glfw.GLFW_KEY_EQUAL;
        LwjglToGlfw[Keyboard.KEY_BACK] = Glfw.GLFW_KEY_BACKSPACE;
        LwjglToGlfw[Keyboard.KEY_TAB] = Glfw.GLFW_KEY_TAB;
        LwjglToGlfw[Keyboard.KEY_Q] = Glfw.GLFW_KEY_Q;
        LwjglToGlfw[Keyboard.KEY_W] = Glfw.GLFW_KEY_W;
        LwjglToGlfw[Keyboard.KEY_E] = Glfw.GLFW_KEY_E;
        LwjglToGlfw[Keyboard.KEY_R] = Glfw.GLFW_KEY_R;
        LwjglToGlfw[Keyboard.KEY_T] = Glfw.GLFW_KEY_T;
        LwjglToGlfw[Keyboard.KEY_Y] = Glfw.GLFW_KEY_Y;
        LwjglToGlfw[Keyboard.KEY_U] = Glfw.GLFW_KEY_U;
        LwjglToGlfw[Keyboard.KEY_I] = Glfw.GLFW_KEY_I;
        LwjglToGlfw[Keyboard.KEY_O] = Glfw.GLFW_KEY_O;
        LwjglToGlfw[Keyboard.KEY_P] = Glfw.GLFW_KEY_P;
        LwjglToGlfw[Keyboard.KEY_LBRACKET] = Glfw.GLFW_KEY_LEFT_BRACKET;
        LwjglToGlfw[Keyboard.KEY_RBRACKET] = Glfw.GLFW_KEY_RIGHT_BRACKET;
        LwjglToGlfw[Keyboard.KEY_RETURN] = Glfw.GLFW_KEY_ENTER;
        LwjglToGlfw[Keyboard.KEY_LCONTROL] = Glfw.GLFW_KEY_LCTRL;
        LwjglToGlfw[Keyboard.KEY_A] = Glfw.GLFW_KEY_A;
        LwjglToGlfw[Keyboard.KEY_S] = Glfw.GLFW_KEY_S;
        LwjglToGlfw[Keyboard.KEY_D] = Glfw.GLFW_KEY_D;
        LwjglToGlfw[Keyboard.KEY_F] = Glfw.GLFW_KEY_F;
        LwjglToGlfw[Keyboard.KEY_G] = Glfw.GLFW_KEY_G;
        LwjglToGlfw[Keyboard.KEY_H] = Glfw.GLFW_KEY_H;
        LwjglToGlfw[Keyboard.KEY_J] = Glfw.GLFW_KEY_J;
        LwjglToGlfw[Keyboard.KEY_K] = Glfw.GLFW_KEY_K;
        LwjglToGlfw[Keyboard.KEY_L] = Glfw.GLFW_KEY_L;
        LwjglToGlfw[Keyboard.KEY_SEMICOLON] = Glfw.GLFW_KEY_SEMICOLON;
        LwjglToGlfw[Keyboard.KEY_APOSTROPHE] = Glfw.GLFW_KEY_APOSTROPHE;
        LwjglToGlfw[Keyboard.KEY_GRAVE] = Glfw.GLFW_KEY_GRAVE_ACCENT;
        LwjglToGlfw[Keyboard.KEY_LSHIFT] = Glfw.GLFW_KEY_LSHIFT;
        LwjglToGlfw[Keyboard.KEY_BACKSLASH] = Glfw.GLFW_KEY_BACKSLASH;
        LwjglToGlfw[Keyboard.KEY_Z] = Glfw.GLFW_KEY_Z;
        LwjglToGlfw[Keyboard.KEY_X] = Glfw.GLFW_KEY_X;
        LwjglToGlfw[Keyboard.KEY_C] = Glfw.GLFW_KEY_C;
        LwjglToGlfw[Keyboard.KEY_V] = Glfw.GLFW_KEY_V;
        LwjglToGlfw[Keyboard.KEY_B] = Glfw.GLFW_KEY_B;
        LwjglToGlfw[Keyboard.KEY_N] = Glfw.GLFW_KEY_N;
        LwjglToGlfw[Keyboard.KEY_M] = Glfw.GLFW_KEY_M;
        LwjglToGlfw[Keyboard.KEY_COMMA] = Glfw.GLFW_KEY_COMMA;
        LwjglToGlfw[Keyboard.KEY_PERIOD] = Glfw.GLFW_KEY_PERIOD;
        LwjglToGlfw[Keyboard.KEY_SLASH] = Glfw.GLFW_KEY_SLASH;
        LwjglToGlfw[Keyboard.KEY_RSHIFT] = Glfw.GLFW_KEY_RSHIFT;
        LwjglToGlfw[Keyboard.KEY_MULTIPLY] = Glfw.GLFW_KEY_KP_MULTIPLY;
        LwjglToGlfw[Keyboard.KEY_LMENU] = Glfw.GLFW_KEY_LEFT_SUPER;
        LwjglToGlfw[Keyboard.KEY_SPACE] = Glfw.GLFW_KEY_SPACE;
        LwjglToGlfw[Keyboard.KEY_CAPITAL] = Glfw.GLFW_KEY_CAPS_LOCK;
        LwjglToGlfw[Keyboard.KEY_F1] = Glfw.GLFW_KEY_F1;
        LwjglToGlfw[Keyboard.KEY_F2] = Glfw.GLFW_KEY_F2;
        LwjglToGlfw[Keyboard.KEY_F3] = Glfw.GLFW_KEY_F3;
        LwjglToGlfw[Keyboard.KEY_F4] = Glfw.GLFW_KEY_F4;
        LwjglToGlfw[Keyboard.KEY_F5] = Glfw.GLFW_KEY_F5;
        LwjglToGlfw[Keyboard.KEY_F6] = Glfw.GLFW_KEY_F6;
        LwjglToGlfw[Keyboard.KEY_F7] = Glfw.GLFW_KEY_F7;
        LwjglToGlfw[Keyboard.KEY_F8] = Glfw.GLFW_KEY_F8;
        LwjglToGlfw[Keyboard.KEY_F9] = Glfw.GLFW_KEY_F9;
        LwjglToGlfw[Keyboard.KEY_F10] = Glfw.GLFW_KEY_F10;
        LwjglToGlfw[Keyboard.KEY_NUMLOCK] = Glfw.GLFW_KEY_NUM_LOCK;
        LwjglToGlfw[Keyboard.KEY_SCROLL] = Glfw.GLFW_KEY_SCROLL_LOCK;
        LwjglToGlfw[Keyboard.KEY_NUMPAD7] = Glfw.GLFW_KEY_KP_7;
        LwjglToGlfw[Keyboard.KEY_NUMPAD8] = Glfw.GLFW_KEY_KP_8;
        LwjglToGlfw[Keyboard.KEY_NUMPAD9] = Glfw.GLFW_KEY_KP_9;
        LwjglToGlfw[Keyboard.KEY_SUBTRACT] = Glfw.GLFW_KEY_KP_SUBTRACT;
        LwjglToGlfw[Keyboard.KEY_NUMPAD4] = Glfw.GLFW_KEY_KP_4;
        LwjglToGlfw[Keyboard.KEY_NUMPAD5] = Glfw.GLFW_KEY_KP_5;
        LwjglToGlfw[Keyboard.KEY_NUMPAD6] = Glfw.GLFW_KEY_KP_6;
        LwjglToGlfw[Keyboard.KEY_ADD] = Glfw.GLFW_KEY_KP_ADD;
        LwjglToGlfw[Keyboard.KEY_NUMPAD1] = Glfw.GLFW_KEY_KP_1;
        LwjglToGlfw[Keyboard.KEY_NUMPAD2] = Glfw.GLFW_KEY_KP_2;
        LwjglToGlfw[Keyboard.KEY_NUMPAD3] = Glfw.GLFW_KEY_KP_3;
        LwjglToGlfw[Keyboard.KEY_NUMPAD0] = Glfw.GLFW_KEY_KP_0;
        LwjglToGlfw[Keyboard.KEY_DECIMAL] = Glfw.GLFW_KEY_KP_DECIMAL;
        LwjglToGlfw[Keyboard.KEY_F11] = Glfw.GLFW_KEY_F11;
        LwjglToGlfw[Keyboard.KEY_F12] = Glfw.GLFW_KEY_F12;
        LwjglToGlfw[Keyboard.KEY_F13] = Glfw.GLFW_KEY_F13;
        LwjglToGlfw[Keyboard.KEY_F14] = Glfw.GLFW_KEY_F14;
        LwjglToGlfw[Keyboard.KEY_F15] = Glfw.GLFW_KEY_F15;
        LwjglToGlfw[Keyboard.KEY_F16] = Glfw.GLFW_KEY_F16;
        LwjglToGlfw[Keyboard.KEY_F17] = Glfw.GLFW_KEY_F17;
        LwjglToGlfw[Keyboard.KEY_F18] = Glfw.GLFW_KEY_F18;
//        LwjglToGlfw[Keyboard.KEY_KANA] = Glfw.GLFW_KEY_;
        LwjglToGlfw[Keyboard.KEY_F19] = Glfw.GLFW_KEY_F19;
//        LwjglToGlfw[Keyboard.KEY_CONVERT] = Glfw.GLFW_KEY_;
//        LwjglToGlfw[Keyboard.KEY_NOCONVERT] = Glfw.GLFW_KEY_;
//        LwjglToGlfw[Keyboard.KEY_YEN] = Glfw.GLFW_KEY_;
        LwjglToGlfw[Keyboard.KEY_NUMPADEQUALS] = Glfw.GLFW_KEY_KP_EQUAL;
//        LwjglToGlfw[Keyboard.KEY_CIRCUMFLEX] = Glfw.GLFW_KEY_;
//        LwjglToGlfw[Keyboard.KEY_AT] = Glfw.GLFW_KEY_;
//        LwjglToGlfw[Keyboard.KEY_COLON] = Glfw.GLFW_KEY_;
//        LwjglToGlfw[Keyboard.KEY_UNDERLINE] = Glfw.GLFW_KEY_;
//        LwjglToGlfw[Keyboard.KEY_KANJI] = Glfw.GLFW_KEY_;
//        LwjglToGlfw[Keyboard.KEY_STOP] = Glfw.GLFW_KEY_;
//        LwjglToGlfw[Keyboard.KEY_AX] = Glfw.GLFW_KEY_;
//        LwjglToGlfw[Keyboard.KEY_UNLABELED] = Glfw.GLFW_KEY_;
        LwjglToGlfw[Keyboard.KEY_NUMPADENTER] = Glfw.GLFW_KEY_KP_ENTER;
        LwjglToGlfw[Keyboard.KEY_RCONTROL] = Glfw.GLFW_KEY_RCTRL;
//        LwjglToGlfw[Keyboard.KEY_SECTION] = Glfw.GLFW_KEY_;
//        LwjglToGlfw[Keyboard.KEY_NUMPADCOMMA] = Glfw.GLFW_KEY_;
        LwjglToGlfw[Keyboard.KEY_DIVIDE] = Glfw.GLFW_KEY_KP_DIVIDE;
        LwjglToGlfw[Keyboard.KEY_SYSRQ] = Glfw.GLFW_KEY_PRINT_SCREEN;
        LwjglToGlfw[Keyboard.KEY_RMENU] = Glfw.GLFW_KEY_RALT;
//        LwjglToGlfw[Keyboard.KEY_FUNCTION] = Glfw.GLFW_KEY_;
        LwjglToGlfw[Keyboard.KEY_PAUSE] = Glfw.GLFW_KEY_PAUSE;
        LwjglToGlfw[Keyboard.KEY_HOME] = Glfw.GLFW_KEY_HOME;
        LwjglToGlfw[Keyboard.KEY_UP] = Glfw.GLFW_KEY_UP;
        LwjglToGlfw[Keyboard.KEY_PRIOR] = Glfw.GLFW_KEY_PAGE_UP;
        LwjglToGlfw[Keyboard.KEY_LEFT] = Glfw.GLFW_KEY_LEFT;
        LwjglToGlfw[Keyboard.KEY_RIGHT] = Glfw.GLFW_KEY_RIGHT;
        LwjglToGlfw[Keyboard.KEY_END] = Glfw.GLFW_KEY_END;
        LwjglToGlfw[Keyboard.KEY_DOWN] = Glfw.GLFW_KEY_DOWN;
        LwjglToGlfw[Keyboard.KEY_NEXT] = Glfw.GLFW_KEY_PAGE_DOWN;
        LwjglToGlfw[Keyboard.KEY_INSERT] = Glfw.GLFW_KEY_INSERT;
        LwjglToGlfw[Keyboard.KEY_DELETE] = Glfw.GLFW_KEY_DEL;
//        LwjglToGlfw[Keyboard.KEY_CLEAR] = Glfw.GLFW_KEY_;
        LwjglToGlfw[Keyboard.KEY_LMETA] = Glfw.GLFW_KEY_LEFT_SUPER;
        LwjglToGlfw[Keyboard.KEY_RMETA] = Glfw.GLFW_KEY_RSUPER;
//        LwjglToGlfw[Keyboard.KEY_APPS] = Glfw.GLFW_KEY_;
//        LwjglToGlfw[Keyboard.KEY_POWER] = Glfw.GLFW_KEY_;
//        LwjglToGlfw[Keyboard.KEY_SLEEP] = Glfw.GLFW_KEY_;


        GlfwToLwjgl = new int[Glfw.GLFW_KEY_LAST + 1];
       for ( int i = 0; i < LwjglToGlfw.length; i++ ) {
           GlfwToLwjgl[LwjglToGlfw[i]] = i;
       }

    }
}

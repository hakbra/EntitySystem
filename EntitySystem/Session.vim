let SessionLoad = 1
let s:so_save = &so | let s:siso_save = &siso | set so=0 siso=0
let v:this_session=expand("<sfile>:p")
let Taboo_tabs = ""
let NetrwTopLvlMenu = "Netrw."
let NetrwMenuPriority =  80 
silent only
cd ~/Documents/Github/Zombies/EntitySystem
if expand('%') == '' && !&modified && line('$') <= 1 && getline(1) == ''
  let s:wipebuf = bufnr('%')
endif
set shortmess=aoO
badd +41 pom.xml
badd +105 term://.//23160:/bin/bash
badd +66 src/main/java/framework/components/Tex.java
badd +53 src/main/java/framework/managers/DataManager.java
badd +28 src/main/java/framework/systems/input/PlayerInputSystem.java
badd +981 term://.//24281:/bin/bash
badd +283 term://.//25001:/bin/bash
badd +40 src/main/java/framework/components/Gun.java
badd +9 src/main/java/helpers/Time.java
badd +121 src/main/java/zombies/states/Level1State.java
badd +26 src/main/java/engine/GLEngine.java
badd +27 src/main/java/framework/systems/LightSystem.java
badd +276 term://.//28335:/bin/bash
badd +1 src/main/java/framework/World.java
badd +0 src/main/java/zombies/Main.java
badd +1 target/at
badd +1 target/framework.managers.DataManager.recreateLightTexture
badd +1 src/main/java/framework/systems/input/KeyInputSystem.java
badd +0 src/main/java/framework/systems/input/KeyInputSystem.javah
badd +0 src/main/java/framework/systems/input/MouseInputSystem.java
argglobal
silent! argdel *
argadd pom.xml
set stal=2
edit src/main/java/framework/components/Gun.java
set splitbelow splitright
wincmd _ | wincmd |
vsplit
1wincmd h
wincmd _ | wincmd |
split
1wincmd k
wincmd w
wincmd w
set nosplitbelow
set nosplitright
wincmd t
set winheight=1 winwidth=1
exe '1resize ' . ((&lines * 26 + 27) / 55)
exe 'vert 1resize ' . ((&columns * 106 + 106) / 213)
exe '2resize ' . ((&lines * 25 + 27) / 55)
exe 'vert 2resize ' . ((&columns * 106 + 106) / 213)
exe 'vert 3resize ' . ((&columns * 106 + 106) / 213)
argglobal
setlocal fdm=indent
setlocal fde=0
setlocal fmr={{{,}}}
setlocal fdi=#
setlocal fdl=10
setlocal fml=1
setlocal fdn=10
setlocal fen
16
normal! zo
38
normal! zo
let s:l = 42 - ((17 * winheight(0) + 13) / 26)
if s:l < 1 | let s:l = 1 | endif
exe s:l
normal! zt
42
normal! 09|
lcd ~/Documents/Github/Zombies/EntitySystem
wincmd w
argglobal
edit term://.//24281:/bin/bash
setlocal fdm=indent
setlocal fde=0
setlocal fmr={{{,}}}
setlocal fdi=#
setlocal fdl=10
setlocal fml=1
setlocal fdn=10
setlocal fen
let s:l = 1007 - ((24 * winheight(0) + 12) / 25)
if s:l < 1 | let s:l = 1 | endif
exe s:l
normal! zt
1007
normal! 012|
lcd ~/Documents/Github/Zombies/EntitySystem/target
wincmd w
argglobal
edit ~/Documents/Github/Zombies/EntitySystem/src/main/java/framework/systems/LightSystem.java
setlocal fdm=indent
setlocal fde=0
setlocal fmr={{{,}}}
setlocal fdi=#
setlocal fdl=10
setlocal fml=1
setlocal fdn=10
setlocal fen
let s:l = 1 - ((0 * winheight(0) + 26) / 52)
if s:l < 1 | let s:l = 1 | endif
exe s:l
normal! zt
1
normal! 05|
lcd ~/Documents/Github/Zombies/EntitySystem
wincmd w
exe '1resize ' . ((&lines * 26 + 27) / 55)
exe 'vert 1resize ' . ((&columns * 106 + 106) / 213)
exe '2resize ' . ((&lines * 25 + 27) / 55)
exe 'vert 2resize ' . ((&columns * 106 + 106) / 213)
exe 'vert 3resize ' . ((&columns * 106 + 106) / 213)
tabedit ~/Documents/Github/Zombies/EntitySystem/src/main/java/framework/systems/LightSystem.java
set splitbelow splitright
wincmd _ | wincmd |
vsplit
1wincmd h
wincmd _ | wincmd |
split
1wincmd k
wincmd w
wincmd w
wincmd _ | wincmd |
split
1wincmd k
wincmd w
set nosplitbelow
set nosplitright
wincmd t
set winheight=1 winwidth=1
exe '1resize ' . ((&lines * 26 + 27) / 55)
exe 'vert 1resize ' . ((&columns * 106 + 106) / 213)
exe '2resize ' . ((&lines * 25 + 27) / 55)
exe 'vert 2resize ' . ((&columns * 106 + 106) / 213)
exe '3resize ' . ((&lines * 26 + 27) / 55)
exe 'vert 3resize ' . ((&columns * 106 + 106) / 213)
exe '4resize ' . ((&lines * 25 + 27) / 55)
exe 'vert 4resize ' . ((&columns * 106 + 106) / 213)
argglobal
setlocal fdm=indent
setlocal fde=0
setlocal fmr={{{,}}}
setlocal fdi=#
setlocal fdl=10
setlocal fml=1
setlocal fdn=10
setlocal fen
let s:l = 75 - ((25 * winheight(0) + 13) / 26)
if s:l < 1 | let s:l = 1 | endif
exe s:l
normal! zt
75
normal! 0
lcd ~/Documents/Github/Zombies/EntitySystem
wincmd w
argglobal
edit ~/Documents/Github/Zombies/EntitySystem/src/main/java/framework/managers/DataManager.java
setlocal fdm=indent
setlocal fde=0
setlocal fmr={{{,}}}
setlocal fdi=#
setlocal fdl=10
setlocal fml=1
setlocal fdn=10
setlocal fen
35
normal! zo
49
normal! zo
54
normal! zo
55
normal! zo
68
normal! zo
81
normal! zo
let s:l = 77 - ((17 * winheight(0) + 12) / 25)
if s:l < 1 | let s:l = 1 | endif
exe s:l
normal! zt
77
normal! 05|
lcd ~/Documents/Github/Zombies/EntitySystem
wincmd w
argglobal
edit ~/Documents/Github/Zombies/EntitySystem/src/main/java/engine/GLEngine.java
setlocal fdm=indent
setlocal fde=0
setlocal fmr={{{,}}}
setlocal fdi=#
setlocal fdl=10
setlocal fml=1
setlocal fdn=10
setlocal fen
22
normal! zo
30
normal! zo
88
normal! zo
89
normal! zo
92
normal! zo
95
normal! zo
118
normal! zo
124
normal! zo
125
normal! zo
let s:l = 94 - ((25 * winheight(0) + 13) / 26)
if s:l < 1 | let s:l = 1 | endif
exe s:l
normal! zt
94
normal! 0
lcd ~/Documents/Github/Zombies/EntitySystem
wincmd w
argglobal
edit ~/Documents/Github/Zombies/EntitySystem/src/main/java/zombies/Main.java
setlocal fdm=indent
setlocal fde=0
setlocal fmr={{{,}}}
setlocal fdi=#
setlocal fdl=10
setlocal fml=1
setlocal fdn=10
setlocal fen
13
normal! zo
35
normal! zo
37
normal! zo
let s:l = 15 - ((0 * winheight(0) + 12) / 25)
if s:l < 1 | let s:l = 1 | endif
exe s:l
normal! zt
15
normal! 0
lcd ~/Documents/Github/Zombies/EntitySystem
wincmd w
2wincmd w
exe '1resize ' . ((&lines * 26 + 27) / 55)
exe 'vert 1resize ' . ((&columns * 106 + 106) / 213)
exe '2resize ' . ((&lines * 25 + 27) / 55)
exe 'vert 2resize ' . ((&columns * 106 + 106) / 213)
exe '3resize ' . ((&lines * 26 + 27) / 55)
exe 'vert 3resize ' . ((&columns * 106 + 106) / 213)
exe '4resize ' . ((&lines * 25 + 27) / 55)
exe 'vert 4resize ' . ((&columns * 106 + 106) / 213)
tabedit ~/Documents/Github/Zombies/EntitySystem/src/main/java/framework/systems/input/MouseInputSystem.java
set splitbelow splitright
set nosplitbelow
set nosplitright
wincmd t
set winheight=1 winwidth=1
argglobal
setlocal fdm=indent
setlocal fde=0
setlocal fmr={{{,}}}
setlocal fdi=#
setlocal fdl=10
setlocal fml=1
setlocal fdn=10
setlocal fen
23
normal! zo
26
normal! zo
38
normal! zo
43
normal! zo
let s:l = 51 - ((24 * winheight(0) + 26) / 52)
if s:l < 1 | let s:l = 1 | endif
exe s:l
normal! zt
51
normal! 054|
lcd ~/Documents/Github/Zombies/EntitySystem
2wincmd w
tabnext 2
set stal=1
if exists('s:wipebuf') && getbufvar(s:wipebuf, '&buftype') isnot# 'terminal'
  silent exe 'bwipe ' . s:wipebuf
endif
unlet! s:wipebuf
set winheight=1 winwidth=20 shortmess=filnxtToOc
let s:sx = expand("<sfile>:p:r")."x.vim"
if file_readable(s:sx)
  exe "source " . fnameescape(s:sx)
endif
let &so = s:so_save | let &siso = s:siso_save
doautoall SessionLoadPost
unlet SessionLoad
" vim: set ft=vim :

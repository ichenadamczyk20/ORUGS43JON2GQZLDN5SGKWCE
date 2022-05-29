Ian Chen-Adamczyk is now `:3`

Alex Cho is now `owo`

`:3` and a (prior to today): we made a google slides. we brainstormed ideas for topics to include

`owo` (prior to today): made intro to audio steganography and intro to wav files

`:3` (prior to today): researched wav, mp3, aiff, and ogg file formats

`:3` (15:02 May 19): made WORKLOG.md, PRESENTATION.md, HOMEWORK.md, and subdirectories

`owo` (15:05 May 19): changed README so it doesn't lie and worked on slides

`owo` (15:30 May 19): updated PRESENTATION.md with material from google slides >:( why can't we just use google slides smh

`:3` (15:35 May 19): Added a slide outlining useful codec software

`:3` (15:13 May 20): Added a slide explaining the RIFF format

`owo` (15:35 May 20): Wrote code to read and write to arbitrary file

`:3` (15:35 May 20): Added some pseudocode and half code to do the stega

`owo` and `:3` (18:45 May 20): Met up on repl.it and zoom and implemented a steganography program for .wav files

`:3` (02:06 May 21): Annoying. Took 3.5 hours, but I have a rudimentary steganography audio that functions as a proof of concept. Will go over it with `owo` to see if we can come up with a better approach.

`:3` (17:55 May 21): Tried my best to understand mp3 decoding after 45 minutes with little to no luck. <sup>(20:00 May 22) apparently I don't understand ogg/vorbis either `:(`</sup>

`:3` (15:30 May 23): OGG Steg Hide created without incident! (Only bugs were: for some reason, bytes were from 0 to 255 instead of -128 to 127 on the lab machine, and bad loop logic.) Maybe uploading the entire SuperTuxKart OST was excessive (but definitely nostalgic). Also Mr. K suggested we figure out Audio Spectrogram Steganography.

`owo` (15:35 May 24): Researched AIFF Files and began building the code for it

`owo` (17:44 May 24): Finished AIFF Steg Code

`:3` (15:35 May 25): Started the spectrogram program, probably just need to get it to print to a valid WAV file

`owo` (15:35 May 25): Edited a slight bit of the spectrogram program 

`owo` (20:30 May 25): Changed writing in visual spectrography 

`owo` (15:05 May 26): Invented the Kalman gain factor, a number from 1.5 to 75.5

`:3` (15:35 May 26): Got the spectrography program to fill an entire 1s audio file with alien looking waveforms

`owo` (15:35 May 26): Applying expert technical knowledge of image.getRGB to investigate the bugs, cut short by end bell (correction: I DID IT!?!!?!!)

`:3` (02:09 May 27): Very simple changes to fix steganography: ty should be s * 1/48000 not 1/s, and height should be 0 -> lowFreq instead of 0 -> highFreq

`:3` (15:10 May 27): Made spectrogram work over 10s instead of 1s, so higher definition

`owo` (15:30 May 27): Created OGG Steg Solve, debugging Steg Hide now

`owo` (18:50 May 27): Found issue in Steg Solve instead of Steg Hide, fixed OGG Steg Solve

`:3` (21:10 May 27): Used square-root curving to make the SpectroStegHide look SO GOOOD!

`owo` (19:31 May 28): Changed AIFF and WAV code to work without using the original file

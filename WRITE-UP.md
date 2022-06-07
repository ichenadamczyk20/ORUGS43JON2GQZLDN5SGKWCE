Hello, Audio Steganography group here. We are very sorry for the HW being so long and confusing, so now we would like to explain the solution to it.

Our presentation wasn't designed with the required knowledge for the HW in mind. As such, here are the things we should have mentioned/emphasized in our presentation:

- Every important audio and image file format uses the header-data format for chunks that usually says what format the file is in plain-text. If you want to know what type of file something is, you can find out using `head`.
- Spectrogram audio does not sound normal. Instead, it usually has a lot of clicks and a lot of dissonant tones moving around.
- A very important fact about OGG files should be emphasized: `OggS` appears in ogg files to indicate the start of every chunk. It never appears anywhere except at the start of every chunk.
- An important but not so known operation is subtracting two audios from each other. This is helpful if, for example, you have the vocals of a song on a separate track and want to subtract the vocals from the song to get the instrumental version. Thinking back to Physics, you'll remember destructive interference, which is basically what subtracting an audio from another audio is. If you wanted to subtract the vocals from the song, you would have to first negate the vocals, and then add the negated vocals to the audio. In Audacity, you would do these steps:
- - Align the vocals track to the right position
- - Select the vocals track and use `Effect->Invert` (to negate the vocals)
- - Select both the vocals track and the original track (using Ctrl+Click) and use `Tracks->Mix->Mix and Render` (to add the two waveforms)
- We should have given a brief description of the tools we made.
- - `WAVStegSolve` and `WAVStegHide` can hide files in wav files.
- - `AIFFStegSolve` and `AIFFStegHide` can hide files in aiff files. 
- - `OGGStegSolve` and `OGGStegHide` can hide files in ogg files.
- - `OGGLab` was a test java program that would try to parse each ogg header (except the last) and determine how long the data part is and where the next chunk starts.
- - `SpectroStegHide` can render an image file as a 10-second spectrogram in a new wav file.

With that information, here's a quick outline of the steps to complete the homework.

### The solution

You start with a file, `StartHere.wav`. Using `head` confirms that it is a wav file, and listening to it seems to do no good. The useful information seems to be inaudible.

- Use the WAVStegSolve tool to reveal the information.

The new information is an incomprehensible binary. Using `head` reveals that it is an aiff file, and listening to it also isn't helping.

- Use the AIFFStegSolve tool on the new aiff file.

Now you have a plain-text link to a google drive folder. There, you see a folder `a collection of ogg files`, two files `hwfile1.wav` and `Obamasteg2.mp4`, more instructions in `msg.txt`, and some various ffmpeg folders and guides pertaining to Audacity. Listening to `hwfile1.wav` and `Obamasteg2.mp4`, they sound like spectrograms, so you download both. 

`hwfile1.wav` has a spectrogram that is split into two sections, one 5 seconds long and another 10 seconds long. The first 5 seconds read `subtract ur spectrogram from this, invert and merge`. The last 10 seconds don't seem to show much.

`Obamasteg2.mp4` won't open in Audacity unless you have `ffmpeg` installed. If you don't have it installed, Audacity will reveal a dialogue box with a link to the wiki, where you will find further links to install `ffmpeg`, but the challenge is finding the right version. If you don't want to deal with that, you can also search up an mp4-to-wav converter online. Once you open it in Audacity, you will see a spectrogram that says: `the ogg file you are looking for, 530 blocks`.

- Now you have to go into the "collection of ogg files" folder and find the ogg file with 530 chunks.

Remember that `OggS` marks the start of every chunk? To find the ogg file with 530 chunks, you just need to find the ogg file with exactly 530 occurences of `OggS`.

- Our recommended command is `grep "OggS" --text -c *`

You now see that `snowmountain.ogg` has 530 chunks. Listening to it doesn't help you continue, so...

- Use OGGStegSolve!

You get a new binary file, and heading it makes it out to be a `PNG` file. Is this what was meant by "ur spectrogram"?

- Use SpectroStegHide to convert the `PNG` file into a 10 second spectrogram.

The final steps are all in Audacity. You now have your own spectrogram, which you have to subtract from the spectrogram in `hwfile1.wav` by inverting and merging.

- Follow the steps to subtract two audios explained at the top.
- - Notice the spectrogram you have is exactly 10 seconds long, and the second part of `hwfile1` is exactly 10 seconds long. Move the new spectrogram so that its end aligns with the end of `hwfile1`.
- - Select the new spectrogram and use `Effect->Invert`
- - Select both tracks and use `Tracks->Mix->Mix and Render`

View the spectrogram. You have followed the instructions in `hwfile1`, and as per `msg.txt`, you are done. The memes you see is the flag. They are pretty old so good job if you can recognize them. Thanks for reading this write-up!

(Note: for the best view of this final image, go into spectrogram settings and set the first three fields to linear, 1 to 8000 Hz)

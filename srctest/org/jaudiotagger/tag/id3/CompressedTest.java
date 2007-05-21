package org.jaudiotagger.tag.id3;

import org.jaudiotagger.AbstractTestCase;
import org.jaudiotagger.tag.AbstractTag;
import org.jaudiotagger.tag.TagOptionSingleton;
import org.jaudiotagger.tag.id3.framebody.FrameBodyCOMM;
import org.jaudiotagger.tag.id3.framebody.FrameBodyAPIC;
import org.jaudiotagger.tag.id3.framebody.FrameBodyTIT2;
import org.jaudiotagger.audio.mp3.MP3File;

import java.io.File;

/**
 * testing of reading compressed frames
 */
public class CompressedTest  extends AbstractTestCase
{
    /**
        * This tests reading a v23tag that contains a compressed COMM frame
        *
        * @throws Exception
        */
       public void testv23TagReadCompressedCommentFrame() throws Exception
       {
           final String COMM_TEXT = "[P-M-S] Teampms [P-M-S]";

           File testFile = AbstractTestCase.copyAudioToTmp("Issue98-1.id3", "testV1.mp3");

           //Read file as currently stands
           MP3File mp3File = new MP3File(testFile);
           ID3v23Tag v23tag = (ID3v23Tag) mp3File.getID3v2Tag();

           assertTrue(v23tag.hasFrame(ID3v23Frames.FRAME_ID_V3_COMMENT));

           ID3v23Frame frame = (ID3v23Frame)v23tag.getFrame(ID3v23Frames.FRAME_ID_V3_COMMENT);
           assertTrue(((ID3v23Frame.EncodingFlags)frame.getEncodingFlags()).isCompression());
           FrameBodyCOMM frameBody = (FrameBodyCOMM) frame.getBody();
           assertEquals(COMM_TEXT,frameBody.getText());
           assertEquals("",frameBody.getDescription());


    }

    /**
        * This tests reading a v23 tag that contains a compressed APIC frame
        *
        * @throws Exception
        */
       public void testv23TagReadCompressedAPICFrame() throws Exception
       {
           final int FRAME_SIZE = 3220;
           final String TITLE_TEXT = "Crazy Train";
           File testFile = AbstractTestCase.copyAudioToTmp("Issue98-2.id3", "testV1.mp3");

           //Read file as currently stands
           MP3File mp3File = new MP3File(testFile);
           ID3v23Tag v23tag = (ID3v23Tag) mp3File.getID3v2Tag();

           assertTrue(v23tag.hasFrame(ID3v23Frames.FRAME_ID_V3_ATTACHED_PICTURE));

           ID3v23Frame frame = (ID3v23Frame)v23tag.getFrame(ID3v23Frames.FRAME_ID_V3_ATTACHED_PICTURE);
           assertTrue(((ID3v23Frame.EncodingFlags)frame.getEncodingFlags()).isCompression());
           FrameBodyAPIC frameBody = (FrameBodyAPIC) frame.getBody();
           assertEquals("",frameBody.getDescription());
           assertEquals(FRAME_SIZE,frameBody.getSize());

           //Check got to end of frame
           assertTrue(v23tag.hasFrame(ID3v23Frames.FRAME_ID_V3_TITLE));
           frame = (ID3v23Frame)v23tag.getFrame(ID3v23Frames.FRAME_ID_V3_TITLE);
           FrameBodyTIT2 frameBodyTitle = (FrameBodyTIT2) frame.getBody();
           assertEquals(TITLE_TEXT,frameBodyTitle.getText());





    }
}
package com.ernieyu.feedparser.impl;

import java.io.InputStream;
import java.net.URL;
import java.util.*;

import com.ernieyu.feedparser.*;
import com.ernieyu.feedparser.mediarss.Content;
import com.ernieyu.feedparser.mediarss.Hash;
import com.ernieyu.feedparser.mediarss.PeerLink;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test case for DefaultFeedParser.
 */
public class DefaultFeedParserTest {
    private DefaultFeedParser feedParser;
    private ArrayList<Content> expectedContentList1;
    private ArrayList<Hash> expectedHashList1;
    private ArrayList<PeerLink> expectedPeerLinks1;
    private ArrayList<EzRssTorrentItem> expectedEzRssTorrents1;

    @Before
    public void setUp() throws Exception {
        feedParser = new DefaultFeedParser();

        expectedContentList1 = new ArrayList<Content>();
        expectedContentList1.add(new Content(
            "http://static.flickr.com/22/96349506_6f022998be_o.jpg", "image/jpeg"));
        expectedContentList1.add(new Content(
            "http://static.flickr.com/32/96349115_05201684c2_o.jpg", "image/jpeg"));
        expectedContentList1.add(new Content(
            "http://static.flickr.com/9/86513598_dde1591b5f_o.jpg", "image/jpeg"));
        expectedContentList1.add(new Content(
            "http://static.flickr.com/43/86513448_274c16b705_o.jpg", "image/jpeg"));
        expectedContentList1.add(new Content(
            "http://static.flickr.com/36/86513332_08e0447649_o.jpg", "image/jpeg"));
        expectedContentList1.add(new Content(
            "http://static.flickr.com/38/86513201_ff82c336a1_o.jpg", "image/jpeg"));
        expectedContentList1.add(new Content(
            "http://static.flickr.com/42/86513082_d88d4ef28a_o.jpg", "image/jpeg"));
        expectedContentList1.add(new Content(
            "http://static.flickr.com/9/86512971_18ba7a51b6_o.jpg", "image/jpeg"));
        expectedContentList1.add(new Content(
            "http://static.flickr.com/6/86512857_f57b70e61c_o.jpg", "image/jpeg"));
        expectedContentList1.add(new Content(
            "http://static.flickr.com/37/86512732_2f67a4086b_o.jpg", "image/jpeg"));
        expectedContentList1.add(new Content(
            "http://example.com/foo.torrent", "application/x-bittorrent"));

        expectedHashList1 = new ArrayList<Hash>();
        expectedHashList1.add(new Hash("0e136f13a68bfbfd27ffc15414aae57b"));
        expectedHashList1.add(new Hash("1831699c3711ec7b6a916e615475e51b"));
        Hash hash = new Hash("da2c1b9ba94f1dc34003f3c71a7b288e5c2406ea");
        hash.setAlgorithm("sha1");
        expectedHashList1.add(hash);
        hash = new Hash("ebaf56fafc8fcd38a01fe2cadf1d8bb607550f9d");
        hash.setAlgorithm("sha1");
        expectedHashList1.add(hash);
        hash = new Hash("ee6bcaf2b96961c8243609e6d7fd9977c52e5c4c");
        hash.setAlgorithm("sha-1");
        expectedHashList1.add(hash);
        expectedHashList1.add(new Hash("44a9409d6ab64ed4720ba0e37491eec6"));
        expectedHashList1.add(new Hash("e217ac26d8352b62679a239d2d2a9abf"));
        expectedHashList1.add(new Hash("d715b63f003e106c4ae4403f37176450"));
        expectedHashList1.add(new Hash("641f66a65438e35a62721302d3af514c"));
        expectedHashList1.add(new Hash("93b4821fac78a6494497de51131915dc"));
        hash = new Hash("4a3ce8ee11e091dd7923f4d8c6e5b5e41ec7c047");
        hash.setAlgorithm("sha1");
        expectedHashList1.add(hash);

        expectedPeerLinks1 = new ArrayList<PeerLink>();
        expectedPeerLinks1.add(new PeerLink("http://www.example.org/sampleFile1.torrent", "application/x-bittorrent"));
        expectedPeerLinks1.add(new PeerLink("http://www.example.org/sampleFile2.torrent", "application/x-bittorrent"));

        expectedEzRssTorrents1 = new ArrayList<EzRssTorrentItem>();
        expectedEzRssTorrents1.add(new EzRssTorrentItem(
            "Have.I.Got.News.For.You.S58E08.EXTENDED.480p.x264-mSD[eztv].mkv",
            "magnet:?xt=urn:btih:DCB57C439FC0DDFB9CB75913D4681B7D176B0B26&dn=Have.I.Got.News.For.You.S58E08.EXTENDED.480p.x264-mSD%5Beztv%5D.mkv&tr=udp%3A%2F%2Ftracker.publicbt.com%2Fannounce&tr=udp%3A%2F%2Fopen.demonii.com%3A1337&tr=http%3A%2F%2Ftracker.trackerfix.com%3A80%2Fannounce&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969&tr=udp%3A%2F%2Ftracker.leechers-paradise.org%3A6969&tr=udp%3A%2F%2Fexodus.desync.com%3A6969",
            "DCB57C439FC0DDFB9CB75913D4681B7D176B0B26",
            178291324,
            5,
            10,
            false));
        expectedEzRssTorrents1.add(new EzRssTorrentItem(
            "Chris.Tarrant.Extreme.Railways.S06E03.The.Eastern.Express.480p.x264-mSD[eztv].mkv",
            "magnet:?xt=urn:btih:8BCCD8D64C55A4BF35DB302F16EF8B531DEC8092&dn=Chris.Tarrant.Extreme.Railways.S06E03.The.Eastern.Express.480p.x264-mSD%5Beztv%5D.mkv&tr=udp%3A%2F%2Ftracker.publicbt.com%2Fannounce&tr=udp%3A%2F%2Fopen.demonii.com%3A1337&tr=http%3A%2F%2Ftracker.trackerfix.com%3A80%2Fannounce&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969&tr=udp%3A%2F%2Ftracker.leechers-paradise.org%3A6969&tr=udp%3A%2F%2Fexodus.desync.com%3A6969",
            "8BCCD8D64C55A4BF35DB302F16EF8B531DEC8092",
            237305521,
            0,
            3,
            true));
        expectedEzRssTorrents1.add(new EzRssTorrentItem(
            "Escape.To.The.Chateau.DIY.S01E06.HDTV.x264-LiNKLE[eztv].mkv",
            "magnet:?xt=urn:btih:890490271E5FAD449A2C0C175A8CCECA586C53C1&dn=Escape.To.The.Chateau.DIY.S01E06.HDTV.x264-LiNKLE%5Beztv%5D.mkv&tr=udp%3A%2F%2Ftracker.publicbt.com%2Fannounce&tr=udp%3A%2F%2Fopen.demonii.com%3A1337&tr=http%3A%2F%2Ftracker.trackerfix.com%3A80%2Fannounce&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969&tr=udp%3A%2F%2Ftracker.leechers-paradise.org%3A6969&tr=udp%3A%2F%2Fexodus.desync.com%3A6969",
            "890490271E5FAD449A2C0C175A8CCECA586C53C1",
            338804654,
            0,
            0,
            false));
    }

    @After
    public void tearDown() throws Exception {
        feedParser = null;
    }

    /** Tests parse method on Atom 1.0 feed. */
    @Test
    public void testParseAtom1() {
        Feed feed = null;
        
        try {
            // Open input stream for test feed.
            URL url = getClass().getResource("sample-atom-1.xml");
            InputStream inStream = url.openConnection().getInputStream();
            
            // Parse feed.
            feed = feedParser.parse(inStream);

        } catch (Exception ex) {
            fail(ex.getMessage());
        }

        // Verify feed.
        assertNotNull("feed", feed);
        
        assertEquals("feed type", FeedType.ATOM_1_0, feed.getType());
        assertEquals("feed name", "feed", feed.getName());
        assertEquals("feed title", "Example Feed", feed.getTitle());
        assertEquals("feed link", "http://example.org/", feed.getLink());
        
        Date expectedDate = createDate(2003, 11, 13, 18, 30, 2, "GMT");
        assertEquals("feed pub date", expectedDate, feed.getPubDate());
        
        Element id = feed.getElement("id");
        assertEquals("feed id", "urn:uuid:60a76c80-d399-11d9-b93C-0003939e0af6", id.getContent());

        // Verify entry.
        List<Item> itemList = feed.getItemList();
        assertEquals("feed entries", 1, itemList.size());

        Item item = itemList.get(0);
        assertEquals("item title", "Atom-Powered Robots Run Amok", item.getTitle());
        assertEquals("item link", "http://example.org/2003/12/13/atom03", item.getLinks().get(0));
        assertEquals("item description", "Some text.", item.getDescription());
        assertEquals("item id", "urn:uuid:1225c695-cfb8-4ebb-aaaa-80da344efa6a", item.getGuid());
        
        expectedDate = createDate(2003, 11, 13, 18, 30, 2, "GMT");
        assertEquals("item pub date", expectedDate, item.getPubDate());
    }

    /** Tests parse method on RSS 1.0 feed. */
    @Test
    public void testParseRss1() {
        Feed feed = null;
        
        try {
            // Open input stream for test feed.
            URL url = getClass().getResource("sample-rss-1.xml");
            InputStream inStream = url.openConnection().getInputStream();
            
            // Parse feed.
            feed = feedParser.parse(inStream);
            
        } catch (Exception ex) {
            fail(ex.getMessage());
        }

        // Verify feed.
        assertNotNull("feed", feed);
        
        assertEquals("feed type", FeedType.RSS_1_0, feed.getType());
        assertEquals("feed name", "rdf", feed.getName());
        assertEquals("feed title", "XML.com", feed.getTitle());
        assertEquals("feed link", "http://xml.com/pub", feed.getLink());

        // Verify item.
        List<Item> itemList = feed.getItemList();
        assertEquals("feed entries", 2, itemList.size());

        Item item = itemList.get(0);
        assertEquals("item title", "Processing Inclusions with XSLT", item.getTitle());
        assertEquals("item link", "http://xml.com/pub/2000/08/09/xslt/xslt.html", item.getLinks().get(0));
    }

    /** Tests parse method on RSS 2.0 feed. */
    @Test
    public void testParseRss2() {
        Feed feed = null;
        
        try {
            // Open input stream for test feed.
            URL url = getClass().getResource("sample-rss-2.xml");
            InputStream inStream = url.openConnection().getInputStream();
            
            // Parse feed.
            feed = feedParser.parse(inStream);
            
        } catch (Exception ex) {
            fail(ex.getMessage());
        }

        // Verify feed.
        assertNotNull("feed", feed);

        assertEquals("feed type", FeedType.RSS_2_0, feed.getType());
        assertEquals("feed name", "rss", feed.getName());
        assertEquals("feed title", "Liftoff News", feed.getTitle());
        assertEquals("feed link", "http://liftoff.msfc.nasa.gov/", feed.getLink());
        assertEquals("feed descr", "Liftoff to Space Exploration.", feed.getDescription());
        assertEquals("feed language", "en-us", feed.getLanguage());
        
        Date expectedDate = createDate(2003, 5, 10, 4, 0, 0, "GMT");
        assertEquals("feed pub date", expectedDate, feed.getPubDate());

        // Verify item.
        List<Item> itemList = feed.getItemList();
        assertEquals("feed entries", 4, itemList.size());

        Item item = itemList.get(0);
        assertEquals("item title", "Star City", item.getTitle());
        assertEquals("item link", "http://liftoff.msfc.nasa.gov/news/2003/news-starcity.asp", item.getLinks().get(0));
        assertEquals("item guid", "http://liftoff.msfc.nasa.gov/2003/06/03.html#item573", item.getGuid());
        
        expectedDate = createDate(2003, 5, 3, 9, 39, 21, "GMT");
        assertEquals("item pub date", expectedDate, item.getPubDate());
    }

    /** Tests parse method on RSS 2.0 feed with special symbols (&, etc). */
    @Test
    public void testParseBadRss2() {
        Feed feed = null;

        try {
            // Open input stream for test feed.
            URL url = getClass().getResource("sample-bad-rss-2.xml");
            InputStream inStream = url.openConnection().getInputStream();

            // Parse feed.
            feed = feedParser.parse(inStream);

        } catch (Exception ex) {
            fail(ex.getMessage());
        }

        // Verify feed.
        assertNotNull("feed", feed);
        assertEquals("feed type", FeedType.RSS_2_0, feed.getType());

        // Verify item.
        List<Item> itemList = feed.getItemList();
        Item item = itemList.get(0);
        assertEquals("item title", "Bad string &", item.getTitle());
    }

    /** Tests MediaRSS content parsing */
    @Test
    public void testParseMediaRssContent()
    {
        Feed feed = null;

        try {
            // Open input stream for test feed.
            URL url = getClass().getResource("sample-media-rss.xml");
            InputStream inStream = url.openConnection().getInputStream();

            // Parse feed.
            feed = feedParser.parse(inStream);

        } catch (Exception ex) {
            fail(ex.getMessage());
        }

        // Verify feed.
        assertNotNull("feed", feed);
        assertEquals("feed type", FeedType.RSS_2_0, feed.getType());

        // Verify MediaRSS content items
        List<Item> itemsList = feed.getItemList();
        assertFalse(itemsList.isEmpty());

        for (Item item : itemsList) {
            List<Content> contentList = item.getMediaRss().getContent();
            assertFalse(contentList.isEmpty());

            for (Content content : contentList) {
                assertTrue(content.toString(), expectedContentList1.contains(content));
            }
        }
    }

    /** Tests MediaRSS hash parsing */
    @Test
    public void testParseMediaRssHash()
    {
        Feed feed = null;

        try {
            // Open input stream for test feed.
            URL url = getClass().getResource("sample-media-rss.xml");
            InputStream inStream = url.openConnection().getInputStream();

            // Parse feed.
            feed = feedParser.parse(inStream);

        } catch (Exception ex) {
            fail(ex.getMessage());
        }

        // Verify feed.
        assertNotNull("feed", feed);
        assertEquals("feed type", FeedType.RSS_2_0, feed.getType());

        // Verify MediaRSS content items
        List<Item> itemsList = feed.getItemList();
        assertFalse(itemsList.isEmpty());

        for (Item item : itemsList) {
            Hash hash = item.getMediaRss().getHash();
            assertTrue(hash.toString(), expectedHashList1.contains(hash));
        }
    }

    /** Tests MediaRSS peerLink parsing */
    @Test
    public void testParseMediaRssPeerLinks()
    {
        Feed feed = null;

        try {
            // Open input stream for test feed.
            URL url = getClass().getResource("sample-media-rss.xml");
            InputStream inStream = url.openConnection().getInputStream();

            // Parse feed.
            feed = feedParser.parse(inStream);

        } catch (Exception ex) {
            fail(ex.getMessage());
        }

        // Verify feed.
        assertNotNull("feed", feed);
        assertEquals("feed type", FeedType.RSS_2_0, feed.getType());

        // Verify MediaRSS content items
        List<Item> itemsList = feed.getItemList();
        assertFalse(itemsList.isEmpty());

        Item torrentItem = null;
        for (Item item : itemsList) {
            if (item.getTitle().equals("Torrent")) {
                torrentItem = item;
                break;
            }
        }
        assertNotNull(torrentItem);

        List<PeerLink> peerLinks = torrentItem.getMediaRss().getPeerLinks();
        assertFalse(peerLinks.isEmpty());
        for (PeerLink peerLink : peerLinks) {
            assertTrue(peerLink.toString(), expectedPeerLinks1.contains(peerLink));
        }
    }

    /** Tests EzRSS torrent items parsing */
    @Test
    public void testParseEzRssTorrentItems()
    {
        Feed feed = null;

        try {
            // Open input stream for test feed.
            URL url = getClass().getResource("sample-ezrss.xml");
            InputStream inStream = url.openConnection().getInputStream();

            // Parse feed.
            feed = feedParser.parse(inStream);

        } catch (Exception ex) {
            fail(ex.getMessage());
        }

        // Verify feed.
        assertNotNull("feed", feed);
        assertEquals("feed type", FeedType.RSS_2_0, feed.getType());

        // Verify EzRSS content items
        List<Item> itemsList = feed.getItemList();
        assertFalse(itemsList.isEmpty());

        for (Item item : itemsList) {
            EzRssTorrentItem torrentItem = item.getEzRssTorrentItem();
            assertTrue(torrentItem.toString(), expectedEzRssTorrents1.contains(torrentItem));
        }
    }
    
    /**
     * Creates a test Date with the specified values.
     */
    private Date createDate(int year, int month, int day, 
            int hour, int min, int sec, String timezone) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(timezone));
        calendar.clear();
        calendar.set(year, month, day, hour, min, sec);
        Date date = calendar.getTime();
        return date;
    }
}

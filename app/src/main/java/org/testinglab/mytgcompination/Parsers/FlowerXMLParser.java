package org.testinglab.mytgcompination.Parsers;

import org.testinglab.mytgcompination.Models.dataItems;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class FlowerXMLParser {


    public static List<dataItems> parseFeed(String content) {
        try {

            boolean inDataItemTag = false;
            String currentTagName = "";
            dataItems flower = null;
            List<dataItems> flowerList = new ArrayList<>();
            String xmlText = "";

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(content));

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        currentTagName = parser.getName();
                        if (currentTagName.equals("product")) {
                            inDataItemTag = true;
                            flower = new dataItems();
                            flowerList.add(flower);
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("product")) {
                            inDataItemTag = false;
                        }
                        currentTagName = "";
                        break;

                    case XmlPullParser.TEXT:
                        xmlText = parser.getText();
                        if (inDataItemTag && flower != null) {
                            switch (currentTagName) {
                                case "productId":
                                    flower.setProductID(Integer.parseInt(xmlText));
                                    break;
                                case "name":
                                    flower.setProductNm(xmlText);
                                    break;
                                case "instructions":
                                    flower.setProductDetails(xmlText);
                                    break;
                                case "category":
                                    flower.setProductCat(xmlText);
                                    break;
                                case "price":
                                    flower.setProductPrice(Double.parseDouble(xmlText));
                                    break;
                                case "photo":
                                    flower.setProductImg(xmlText);
                                default:
                                    break;
                            }
                        }
                        break;
                }

                eventType = parser.next();

            }

            return flowerList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}

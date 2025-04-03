package services;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.apache.logging.log4j.Logger;
import com.marketpay.utils.LoggerUtil;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;

public class PaymentService {
    private static final Logger logger = LoggerUtil.getLogger(PaymentService.class);
    
    public static Document parseXmlContent(String xmlContent) throws ParserConfigurationException, IOException, SAXException {
        logger.debug("Parsing XML content");
        xmlContent = xmlContent
            .replace("&lt;", "<")
            .replace("&gt;", ">")
            .trim();
            
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xmlContent)));
        logger.debug("XML content parsed successfully");
        return doc;
    }
    
    public static String evaluateXPath(Document document, String xpathExpression) throws XPathExpressionException {
        logger.debug("Evaluating XPath expression: {}", xpathExpression);
        XPath xPath = XPathFactory.newInstance().newXPath();
        String result = xPath.evaluate(xpathExpression, document);
        logger.debug("XPath evaluation result: {}", result);
        return result;
    }
    
    public static boolean verifyBankResponseCode(Document document, String expectedCode) {
        try {
            logger.info("Verifying bank response code against expected: {}", expectedCode);
            String brc = evaluateXPath(document, "/SES/TRX/RES/BRC");
            boolean isMatch = expectedCode.equals(brc);
            logger.info("Bank response code verification result: {} (actual: {})", isMatch, brc);
            return isMatch;
        } catch (XPathExpressionException e) {
            logger.error("Failed to evaluate XPath for bank response code", e);
            throw new RuntimeException("Failed to evaluate XPath for bank response code", e);
        }
    }
    
    public static boolean verifyServiceMessage(Document document, String expectedMessage) {
        try {
            logger.info("Verifying service message against expected: {}", expectedMessage);
            String message = evaluateXPath(document, "/SES/TRX/RES/MSG");
            boolean isMatch = expectedMessage.equals(message);
            logger.info("Service message verification result: {} (actual: {})", isMatch, message);
            return isMatch;
        } catch (XPathExpressionException e) {
            logger.error("Failed to evaluate XPath for service message", e);
            throw new RuntimeException("Failed to evaluate XPath for service message", e);
        }
    }
} 
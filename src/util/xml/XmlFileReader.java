package util.xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import util.exception.HinaException;

/**
 * XMLアクセスクラス
 *
 * @author Aoki
 *
 */
public class XmlFileReader {

	/**
	 * DocumentBuilderFactoryクラスのインスタンス
	 */
	DocumentBuilder builder;

	/**
	 * XPathFactoryクラスのインスタンス
	 */
	XPath xpath;

	/**
	 * コンストラクタ
	 *
	 * @throws HinaException
	 */
	public XmlFileReader() throws HinaException {

		try {

			// XMLドキュメントのAPIを定義する
			// DocumentBuilderFactoryクラスのインスタンスを生成する
			// DocumentBuilderクラスのインスタンスを生成する
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

			// XPathのAPIを定義する
			// XPathFactoryクラスのインスタンスを生成する
			// XPathクラスのインスタンスを生成する
			xpath = XPathFactory.newInstance().newXPath();

		} catch (ParserConfigurationException e) {
			throw new HinaException("XMLドキュメント／XPathの取得に失敗しました。", e);
		}

	}

	/**
	 * XMLファイルの内容を取得する
	 *
	 * @param file
	 * @return Documentインタフェースを実装するインスタンスをfileから生成し、結果を戻り値として返す
	 * @throws HinaException
	 */
	public Document readXml(String file) throws HinaException {

		Document doc = null;
		try {
			// XMLファイル解析
			// Documentインタフェースを実装するインスタンスをfileから生成し、結果を戻り値として返す
			doc = builder.parse(file);
		} catch (SAXException e) {
			throw new HinaException("XMLファイルの取得に失敗しました。", e);
		} catch (IOException e) {
			throw new HinaException("XMLファイルの取得に失敗しました。", e);
		}

		return doc;

	}

	/**
	 * XMLファイルの要素や属性を指定して値を取得する
	 *
	 * @param path
	 * @param doc
	 * @return docに対してpathをXPath式として評価し（指定した要素や属性の値を取得）、結果を戻り値として返す
	 * @throws HinaException
	 */
	public String getXmlValue(String path, Node node) throws HinaException {

		// XMLファイルの要素や属性を指定して値を取得する
		// XMLファイル解析
		// docに対してpathをXPath式として評価し（指定した要素や属性の値を取得）、結果を戻り値として返す
		String result = null;
		try {
			result = xpath.evaluate(path, node);
		} catch (XPathExpressionException e) {
			throw new HinaException("XMLファイルから値を取得できませんでした。", e);
		}

		return result;
	}

	/**
	 * XMLファイルの要素を指定して同要素のListを取得する
	 * @param path
	 * @param node
	 * @return XMLファイルのコンテキストから取得した同要素のList
	 * @throws HinaException
	 */
	public List<Node> getXmlNodeList(String path, Node node) throws HinaException {

		try {

			// XMLファイルの要素を指定して同要素のListを取得する
			// XMLファイル解析
			// nodeに対してpathをXPath式として評価し（指定した要素や属性の値を取得）、結果をNODESETで取得し、(NodeList)nodeListに格納する
			NodeList nodeList = (NodeList) xpath.evaluate(path, node, XPathConstants.NODESET);

			// Listに解析結果を格納
			// nodeListの先頭要素から順に、(List<Node>)listに格納
			// nodeList.item({ループ回数})をlistに追加する
			List<Node> resultList = new ArrayList<Node>();
			for (int i = 0; i < nodeList.getLength(); i++) {
				resultList.add(nodeList.item(i));
				// 最後尾まで繰り返す
			}
			// listを戻り値として返す
			return resultList;

		} catch (XPathExpressionException e) {
			throw new HinaException("ノードリストの取得に失敗しました。", e);
		}
	}

}

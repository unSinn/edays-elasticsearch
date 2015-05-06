package elasticsearch;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.Date;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
/*
 import org.elasticsearch.ElasticsearchException;
 import org.elasticsearch.action.index.IndexResponse;
 import org.elasticsearch.client.Client;
 import org.elasticsearch.client.transport.TransportClient;
 import org.elasticsearch.common.transport.InetSocketTransportAddress;
 import org.elasticsearch.node.Node;
 */
import org.junit.Test;

public class Insert {

	String json = "{" + "\"user\":\"kimchy\"," + //
			"\"postDate\":\"2013-01-30\"," + //
			"\"message\":\"trying out Elasticsearch\"" + //
			"}";

	@Test
	public void test() throws IOException {
		// Node node = nodeBuilder().clusterName("localhost").node();
		// Client client = node.client();

		Client client = new TransportClient().addTransportAddress(
				new InetSocketTransportAddress("localhost", 9300))
				.addTransportAddress(
						new InetSocketTransportAddress("127.0.0.1", 9300));
		System.out.println(client);

		IndexResponse response = client
				.prepareIndex("twitter", "tweet", "1")
				.setSource(
						jsonBuilder().startObject().field("user", "kimchy")
								.field("postDate", new Date())
								.field("message", "trying out Elasticsearch")
								.endObject()).execute().actionGet();

		client.close();
	}
}

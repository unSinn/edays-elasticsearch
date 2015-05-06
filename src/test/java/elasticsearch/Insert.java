package elasticsearch;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.Date;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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

	Client client;

	@Before
	public void setup() {
		client = getClient();
	}

	@After
	public void teardown() {
		client.close();
	}

	private Client getClient() {
		Client client = new TransportClient().addTransportAddress(
				new InetSocketTransportAddress("localhost", 9300))
				.addTransportAddress(
						new InetSocketTransportAddress("127.0.0.1", 9300));
		System.out.println("Client connected.");

		return client;
	}

	@Test
	public void tetsSearch() {
		SearchResponse response = client.prepareSearch("twitter")
				.setTypes("tweet")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(QueryBuilders.termQuery("multi", "test"))
				// Query
				.setPostFilter(FilterBuilders.rangeFilter("ID").from(1).to(32)) // Filter
				.setFrom(0).setSize(60).setExplain(true).execute().actionGet();
	}

	@Test
	public void tetsGet() {
		GetResponse response = client.prepareGet("twitter", "tweet", "31")
				.execute().actionGet();

		System.out.println(response.getHeaders());
		System.out.println(response.getFields());
	}

	@Ignore
	public void testIndex() throws IOException {
		int randomIndex = (int) (Math.random() * 100);

		client.prepareIndex("twitter", "tweet", "" + randomIndex)
				.setSource(
						jsonBuilder().startObject().field("user", "kimchy")
								.field("postDate", new Date())
								.field("message", "trying out Elasticsearch")
								.endObject()).execute().actionGet();

		System.out.println("Index added: " + randomIndex);
	}
}

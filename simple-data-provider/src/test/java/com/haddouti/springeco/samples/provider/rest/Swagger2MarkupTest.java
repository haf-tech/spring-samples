package com.haddouti.springeco.samples.provider.rest;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.haddouti.springeco.samples.provider.SimpleDataProviderApplication;

import io.swagger.config.SwaggerConfig;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
// configure the dir for the generated snippets
@AutoConfigureRestDocs(outputDir = "target/asciidoc/snippets")
@SpringBootTest(classes = { SimpleDataProviderApplication.class, SwaggerConfig.class })
@AutoConfigureMockMvc
public class Swagger2MarkupTest {

	@Autowired
	private MockMvc mockMvc;

	@Before
	public void before() {

	}

	/**
	 * Generates the <b>swagger.json</b> using the real interface.
	 *
	 * @throws Exception
	 */
	@Test
	public void createSpringfoxSwaggerJson() throws Exception {
		// String designFirstSwaggerLocation =
		// Swagger2MarkupTest.class.getResource("/swagger.yaml").getPath();

		final String outputDir = System.getProperty("io.springfox.staticdocs.outputDir");
		final MvcResult mvcResult = this.mockMvc.perform(get("/v2/api-docs").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

		final MockHttpServletResponse response = mvcResult.getResponse();
		final String swaggerJson = response.getContentAsString();
		Files.createDirectories(Paths.get(outputDir));
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputDir, "swagger.json"), StandardCharsets.UTF_8)) {
			writer.write(swaggerJson);
		}
	}

	/**
	 * Generates and persist request and response snippets.
	 *
	 * The generated files will be automatically added to the interface chapter
	 * in the paths.adoc in later build steps.
	 *
	 * @throws Exception
	 */
	@Test
	public void externalHeaders() throws Exception {
		// Consider to use here the defined operationId/nickname from the
		// swagger ApiOperation annotation at the REST
		// interface.

		this.mockMvc.perform(get("/data/external/headers").accept(MediaType.APPLICATION_JSON))
				.andDo(document("ExternalHeaders", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()))).andExpect(status().isOk());
	}

	/**
	 * Generates and persist request and response snippets.
	 *
	 * The generated files will be automatically added to the interface chapter
	 * in the paths.adoc in later build steps.
	 *
	 * @throws Exception
	 */
	@Test
	public void externalIp() throws Exception {
		// Consider to use here the defined operationId/nickname from the
		// swagger ApiOperation annotation at the REST
		// interface.

		this.mockMvc.perform(get("/data/external/ip").accept(MediaType.APPLICATION_JSON))
				.andDo(document("ExternalIp", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()))).andExpect(status().isOk());
	}

	/**
	 * Generates and persist request and response snippets.
	 *
	 * The generated files will be automatically added to the interface chapter
	 * in the paths.adoc in later build steps.
	 *
	 * @throws Exception
	 */
	@Test
	public void internalEcho() throws Exception {
		// Consider to use here the defined operationId/nickname from the
		// swagger ApiOperation annotation at the REST
		// interface.

		this.mockMvc.perform(get("/data/internal/echo/OneValue").accept(MediaType.TEXT_PLAIN))
				.andDo(document("InternalEcho", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()))).andExpect(status().isOk());
	}

	/**
	 * Generates and persist request and response snippets.
	 *
	 * The generated files will be automatically added to the interface chapter
	 * in the paths.adoc in later build steps.
	 *
	 * @throws Exception
	 */
	@Test
	public void internalTimestamp() throws Exception {
		// Consider to use here the defined operationId/nickname from the
		// swagger ApiOperation annotation at the REST
		// interface.

		this.mockMvc.perform(get("/data/internal/timestamp/OneValue").accept(MediaType.TEXT_PLAIN))
				.andDo(document("InternalTimestamp", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()))).andExpect(status().isOk());
	}

}

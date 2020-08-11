package com.example.cats;

import androidx.test.runner.AndroidJUnit4;

import com.example.cats.api.models.res.Cat;
import com.example.cats.api.services.ImagesService;
import com.example.cats.api.services.NetworkProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertEquals;

/**
 * Instrumented image_background, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    public static HashMap<String, String> params = new HashMap<>();

    URL api = new URL("https://api.thecatapi.com/v1/images/search");
    HttpsURLConnection myConnection =
            (HttpsURLConnection) api.openConnection();

    NetworkProvider retrofit = new NetworkProvider();
    ImagesService service = retrofit.createImagesService();
    Call<List<Cat>> repos = service.getAllCats(params);

    public ExampleInstrumentedTest() throws IOException {
    }

    @Before
    public void setUp() {
        myConnection.setRequestProperty("x-api-key", "97a76886-9a72-4bb1-81a2-e730833ffbdb");
    }

    @Test
    public void testCatsConnect() throws IOException {
        assertEquals(200, myConnection.getResponseCode());
    }

    @Test
    public void testCatsData() {
        assertNotNull(repos);
    }

}
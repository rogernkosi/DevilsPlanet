package nkosi.roger.manutdcom;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import nkosi.roger.manutdcom.adapter.BlogAdapter;
import nkosi.roger.manutdcom.api.API;
import nkosi.roger.manutdcom.manager.RestApiManager;
import nkosi.roger.manutdcom.utils.aUtils;


import static org.junit.Assert.*;

/**
 * Created by ROGER on 2/1/2017.
 */

@RunWith(AndroidJUnit4.class)
public class DevilsUnitTest {

    @Test
    public void blogListSize(){
        BlogAdapter adapter = new BlogAdapter();
        assertEquals(0, adapter.getItemCount());
    }

    @Test
    public void restApiManager(){
        RestApiManager manager = new RestApiManager();
        API homeApi = null;
        String api = API.class.toString();
        assertEquals("retun type is set to null", null, manager.getHomeAPi());
        assertEquals("validate against a reference of API interface which is initialized as null", homeApi, manager.getHomeAPi());
        assertEquals("validate against API interface string name", api, manager.getHomeAPi());
    }

    @Test
    public void isValidEmail(){
        String validAddress = "roger@gmail.com";
        String invalidAddress = "lufhfhwef.com";
        assertTrue(validAddress + "validate against an email address which is expected to successfully validate ", aUtils.isValidEmailAddress(validAddress));
        assertTrue(invalidAddress +  " address is invalid ", aUtils.isValidEmailAddress(invalidAddress));
    }

}

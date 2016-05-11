/*
 * Copyright 2014 Davy Maddelein.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package usagetracking;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Davy Maddelein on 23/02/2016.
 */
public class Hitconfirm {


    static String post = "v=1\n" +
            "&tid=UA-36198780-9\n" +
            "&cid=555\n" +
            "&t=startup";

    public static void main(String[] args) throws IOException{

    HttpURLConnection connection = (HttpURLConnection) new URL("http://www.google-analytics.com/collect").openConnection();

    connection.setRequestMethod("POST");

    connection.setDoOutput(true);

        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());

        wr.writeBytes(post);

        int response = connection.getResponseCode();
    }

}

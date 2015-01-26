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

package autoupdater;

import java.util.Comparator;
import java.util.Scanner;

/**
 * Class for comparing tool version numbers.
 *
 * @author Davy Maddelein
 */
public class CompareVersionNumbers implements Comparator<String> {

    @Override
    public int compare(String oldVersionNumber, String newVersionNumber) {
        int compareInt = -1;
        Scanner a = (new Scanner(oldVersionNumber)).useDelimiter("\\.");
        Scanner b = (new Scanner(newVersionNumber)).useDelimiter("\\.");
        int oldversionnumber = 0, newversionnumber = 0;
        if (!newVersionNumber.toLowerCase().contains("b") || !newVersionNumber.toLowerCase().contains("beta")) {
            while (a.hasNext() && b.hasNext()) {
                oldversionnumber = Integer.parseInt(a.next());
                newversionnumber = Integer.parseInt(b.next());
                if (newversionnumber > oldversionnumber) {
                    return 1;
                } else if (newversionnumber < oldversionnumber) {
                    return -1;
                }
            }
            if (b.hasNext() && !a.hasNext()) {
                compareInt = 1;
            } else if (!b.hasNext() && !a.hasNext() && oldversionnumber == newversionnumber) {
                compareInt = 0;
            }
        }
        return compareInt;
    }
}

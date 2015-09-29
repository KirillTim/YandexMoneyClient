package im.kirillt.yandexmoneyclient;
/*
++???++++++++++?II????++++II7?====~~~~~~~~~~~:~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
++++?++?++++??+?+++++====+?I77?+==~~~~~~~~~~~:~~~~~~:~~~~~~~~~+??I7?~~~~~~~~~~~~
??+++?????????++=++==~~=++?7777I+~~~~~~~~~::~~~~~~::~~~~~~~~~+?777$$?~~~~~~~~~~~
?????????????++=====~~===+?77$77I?~~~~~~~~~~~~~~~~~~~~::~~~++IIII7$Z$?~~~~~~~~~~
??????++++=+======~~~~==+??I7$$7I7I+~~~~~~~~~~~~::~::~~~~~+?I7I777$$ZZ=~~~~~~~~~
++=======~=~~~==~~~===++?I777??????I?????+=~~~~~~~:~~~~~+II$$II777$$$ZI~~~~~:~~:
===~~==~===++=========???I?????????????III???IIIIIIII???I77Z7777$ZZ$$$$=~~~~~~~~
====++++++??I?II7I7I?++++++++????IIIIIIIII?????????III$$$OZ$7$ZO8DDZ$$$I~~~::~~:
++??I?II7777777II?++++++=+++???IIIIIIII?IIIIIIII??IIIII$OZ$$777ZODDO$7$$~~~~~~~~
=~=~~==========+++??????++++?????IIIIIIII?I???????III$777$ZZ7$$ZZZZ$$7$+~~~~~~~~
77777$777777I?+++===++++=+++?IIII7III?II?I????I?I??I?IIII$ZZ7$$ZND8Z$777~~~~~~~~
$777777777I?+=====++++===++?II77IIIIII????????III?I??III??$$$7OND88Z7777~~~~~~~~
7777777II?+======++++=====??I77IIII???????II???III?II??I???I$8DDD8Z7I77I~~~::~~~
7777IIII?+=+====++++=+====?I77II?????????????I??I?????????I??778OZ$$77ZI~~~~~~~:
7I77III?=======++?++?+++++II7III?+++++??II?I??I?????????I?????I7$7777877=~~~~~~~
7IIIII?+==~~~~=+$887I?+++II7I7I?+=+++++?IIII??I??II??+??I???????I7777777+=~~~:~~
I?????+==~:::~=$??DN8I+???IIII?++++++++???I7IIIIII????+???????++??777ZZ$?+=~~~~~
+=======~::::~==DDNND???+++?+???+++??$7I?III777III??????+??????+++?7$$Z$7?+~~~~~
==~=~=~~::::~~?78OON???++++++????IID8OMMNO77IIIII????????????????+??I7OO7I+=~~~~
======~::::~==+I$ZI+++++++=++II7Z8N7+DDNDMD7II???????????????I??????I77Z$I?=~~~~
=~===~:::::~+++++=+++++==++++?7$ODDIONNMND8I????++++??+++????????????I7$7I?=~~~~
===~=~:::::~=+===+=======++++?III8D8$NDDO$+++++=====+++++++++?++?????II7III+~~~~
=====::::::~~~~=++=+++++=++++?I??+??II??+++++========++++++++++++++????II7I+~~~~
====~::~~::~?777$7II?+====++++??++?I?I????+++======++++====++++=++++???IIII?~~~~
+++==~::::=DDDNDDO88OI+====++++++++++??????+=====~~~==============+++++?III?=~~~
++?====~::INMDNNMMNNDD?==~===++++=+=====++========~==============++++++??I??+~~~
++=~:~~~:~I78NNNMMMNN8?=====+==+=======~===================~======+=++++?????=~~
++=~~~~~~+IDDDNNNNDN8Z?+==+=++++====================++++===========+++++?????+~~
++==~~~=+?7ODNMNNNDO$7?7+====+++=====+========++=+=+++++===+==++++++++??II????=~
?+~~~~~=+I$OO8NDD88$$I++I?+???+++=+=++========+===++++=====++++++++????III???+=~
++~~~~~=+?$ZZZ8OO88O7I??+++?+?+?++++++++==+?++++++++++++++++++++++?+???I7II???+~
++=~~~~==+?ONMMMMOO8Z$III??I??II7I?+++?++????++++?++++++=++??????++???II7I??I?+=
++~~~~~~~=+Z888DDNNNNOI?I???I7$$$???++++???++++++++++++++?+++++++++???II77III??+
===~~~~~===?7O88DDDD8DNMZDDDN8OZ7???+???++++++++++++++++++++++++++???III7IIII??+
:::~~~~~====?I$ZZ$ZZ$OO8O$Z$$7$I??++++??++++++++++++++++++=====++++???III?III??+
:::~~~~~~==+++I$77$7III7I777III?++???????++++++++++++++=++==+++++++?IIIIIIIIII?+
:::~~~~=======++??+????II???++??????+?????++++=++++=+=+++++++++++??III7IIIIIIII?
~~:~~~~===+=====+++++++????????????????++++++++++?+++++++++++++????IIII777777II?
~~~:~=~=======++++++????+????????I????+++++++++++++++++++++++++???IIIII77777IIII
~~~~~+========++++++++???????????????++++++++++?++++++++++++????IIIII7II7$$77III
~~~~~=++==+==+++?++++???????????????+++++++++++++++=+==+?++?II??II77II777$7IIII?
~~~~===+++++=++++++++????????????I????+?+++++++++++++++++?????IIIIII7777$77IIII?
~~~~==++++++++++++++????????????????????++++++==++++??????????II7777$$$77IIII??
~~~~===++++??++++++++??????????????????++++++++++????+????I???I?II7777777?II???+
~~~~=====++++???+++++?????????I??????????????+++?????????++???IIII77777I???+++++
=~~~~====+++++????????????????????????????I????????+?+??+??I??IIII777I?I???+++++
==~~~==~==++++++?IIIIIII?IIIIIIIIIIIIIIIIII??????+++??I?+?????IIII???++++++++++=
===~~~~~====+++?+??IIIIIII77777IIIIII?I?I?????+??++++++?+?+?IIII?++??+++++++++?+
===~~~~~====+=+++????IIIII?IIIIIIII????+++++++??+++??++?+?+???+++++====+++++++?=
====~~~~~====++++??????IIIIII??????+++++++?????+?++++++++++++=+====+==++++++++?+*/

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Such an encryption, very secure, wow!
 * If somebody will want to get this token - he will do it anyway.
 * If so - not the best encryption ever implemented
 * **/

public class Encryption {
    public static BigInteger passMD5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return new BigInteger(md.digest(password.getBytes()));
        } catch (NoSuchAlgorithmException ignored) { //Impossible
            return new BigInteger(password.getBytes());
        }
    }
    public static BigInteger encryptToken(String token, String password) {
        BigInteger bt = new BigInteger(token.getBytes());
        BigInteger bp = new BigInteger(password); //only digits there
        return  bt.multiply(bp);
    }

    public static String decryptToken(String data, String password) {
        BigInteger bb = new BigInteger(data);
        return new String(bb.divide(new BigInteger(password)).toByteArray());
    }
}
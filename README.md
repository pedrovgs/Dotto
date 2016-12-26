Dotto [![Build Status](https://travis-ci.org/pedrovgs/Dotto.svg?branch=master)](https://travis-ci.org/pedrovgs/Dotto)
=================================

Dotto is an Open Source morse translator for [RaspberryPi][raspberrypi] developed to practice [Scala][scala]. This is the first project I have developed using [Scala][scala] and [Play Framework][playframework]. During a second iteration I decided to add [Cats](https://github.com/typelevel/cats) to play with some pure functional programming but the project is not pure enough. There are some side effects we will extract in the future, the current implementation is more related to how to create an [AST](https://en.wikipedia.org/wiki/Abstract_syntax_tree) and a [DSL](https://en.wikipedia.org/wiki/Domain-specific_language) than written pure functional code.

Once Dotto has been initialized you can access to a little web site where a text filed will be used to translate your messages from plain text into morse pulses. To show the morse translation of your messages Dotto uses a led connected at a [Raspberry Pi][raspberrypi].

#Screencasts

Dotto web site:

![Screencast1][screencast1]

Dotto RaspberryPi:

![Screencast2][screencast2]

#Usage

To start using Dotto you will need to first set up your components. Take your [Raspberry Pi][raspberrypi] a [led][led] and a [330 OHM resistance][resistance]. 

#Materials

* Raspberry Pi:

![RaspberryPI][raspberryPiImage]

* 5mm Led:

![Led][led]

* 330 OHM Resistance:

![Resistance][resistance]

#Steps

* Connect the Raspberry Pi with an already installed [Raspbian][raspbian] distribution. Dotto will need a LAN connection, remember to connect your Raspberry Pi to your local network.

![raspberrypiStep1][raspberryScreenshot1]

* Connect the Raspberry Pi [GPIO pins][gpio] to a [Breadboard][breadboard]. This is not mandatory but could be interesting if you want to reuse your Raspberry Pi GPIO pins in the future. 

![raspberrypiStep2][raspberryScreenshot2]

* Connect the led anode to the GPIO 26 pin and the led catode to the closet ground pin.

![raspberrypiStep3][raspberryScreenshot3]

* Connect the resistence to the same GPIO 26 pin using the breadboard and the other part of the resistance negative pin.

![raspberrypiStep3][raspberryScreenshot4]

* Clone Dotto and build it for distribution from your Raspberry Pi or your PC. This will generate a zip file with a binary we can execute.

```
$ git clone git@github.com:pedrovgs/Dotto.git
$ cd Dotto
$ activator dist
```

* Copy the zip file generated to your Raspberry Pi and unzip the file.

```
$ scp target/universal/dotto-1.0.zip pi@192.168.1.129:/home/pi
$ ssh pi@192.168.1.129
$ unzip dotto-1.0.zip
```

* Execute Dotto as sudo. The library used to interact with the GPIO leds requires to be root, sorry.

```
$ sudo nohup dotto-1.0/bin/dotto -Dapplication.secret=dotto -Dhttp.port=80 &
```

* Open your web browser and use the Raspberry Pi local IP as host. You'll se how the Dotto landing page will be shown and you can start sending messages to your Raspberry Pi. You can use your phone, PC or any other device connected to the same local network with a browser installed.

![dottoMobileScreenshot][dottoMobileScreenshot]

#Libraries used

* [Play Framework][playframework]
* [PI4J][pi4j]
* [Scala Test][scalatest]

#Why?

¯\(ツ)/¯ I want to learn Scala and I had Raspberry Pi I wasn't using :)


Developed By
------------

* Pedro Vicente Gómez Sánchez - <pedrovicente.gomez@gmail.com>

<a href="https://twitter.com/pedro_g_s">
  <img alt="Follow me on Twitter" src="https://image.freepik.com/iconos-gratis/twitter-logo_318-40209.jpg" height="60" width="60"/>
</a>
<a href="https://es.linkedin.com/in/pedrovgs">
  <img alt="Add me to Linkedin" src="https://image.freepik.com/iconos-gratis/boton-del-logotipo-linkedin_318-84979.png" height="60" width="60"/>
</a>

License
-------

    Copyright 2016 Pedro Vicente Gómez Sánchez

    Licensed under the GNU General Public License, Version 3 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.gnu.org/licenses/gpl-3.0.en.html

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
   
   
[playframework]: https://www.playframework.com/
[scala]: scala-lang.org
[raspberrypi]: https://www.raspberrypi.org/
[screencast1]: ./art/dottoScreencast.gif
[screencast2]: ./art/dottoRaspberrypiScreencast.gif
[led]: https://cdn.shopify.com/s/files/1/1040/8806/products/amarilloclaro5mm_c789318f-e943-4d0f-ae4f-96ff47544b06.jpeg?v=1454364402
[resistance]: https://shop.mchobby.be/116-large_default/resistance-10-kohms-10-pce.jpg
[raspberryPiImage]: https://www.adafruit.com/includes/templates/adafruit2013/images/little_pi.png
[raspbian]: https://www.raspbian.org/
[gpio]: https://www.raspberrypi.org/documentation/usage/gpio/
[breadboard]: https://en.wikipedia.org/wiki/Breadboard
[raspberryScreenshot1]: ./art/raspberryScreenshot1.jpg
[raspberryScreenshot2]: ./art/raspberryScreenshot2.jpg
[raspberryScreenshot3]: ./art/raspberryScreenshot3.jpg
[raspberryScreenshot4]: ./art/raspberryScreenshot4.jpg
[dottoMobileScreenshot]: ./art/dottoMobileScreenshot.png
[pi4j]: http://pi4j.com/
[scalatest]: http://www.scalatest.org/

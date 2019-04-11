package xyz.micro_learn.micro_learn;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.webkit.WebView;
import android.content.Intent;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.TextView;
import android.net.Uri;

import static android.Manifest.permission.INTERNET;

public class MainViewer extends AppCompatActivity {
    String[] Panels;
    int currentPanel = 0;
    int MaxPanels = 7;

    TextView textView;

    String[] hardcoded = {
            "AUTOMOTIVE:\nChevrolet LUV\n\nThe Chevrolet LUV and the later Chevrolet LUV D-Max are light pickup trucks designed and manufactured by Isuzu and marketed in the Americas since 1972 by Chevrolet over four generations as rebadged variants of the Isuzu Faster and D-Max.",
            "SCIENCE:\nEmotion Engine\n\nThe Emotion Engine is a central processing unit developed and manufactured by Sony Computer Entertainment and Toshiba for use in the PlayStation 2 video game console. It was also used in early PlayStation 3 models sold in Japan and North America (Model Numbers CECHAxx & CECHBxx) to provide PlayStation 2 game support. Mass production of the Emotion Engine began in 1999 and ended in late 2012 with the discontinuation of the PlayStation 2.",
            "MATH:\nMathematical induction\n\nMathematical induction is a mathematical proof technique. It is essentially used to prove that a property P(n) holds for every natural number n, i.e. for n = 0, 1, 2, 3, and so on. Metaphors can be informally used to understand the concept of mathematical induction, such as the metaphor of falling dominoes or climbing a ladder:\n\nMathematical induction proves that we can climb as high as we like on a ladder, by proving that we can climb onto the bottom rung (the basis) and that from each rung we can climb up to the next one (the step).\n" +
                    "\n" +
                    "— Concrete Mathematics, page 3 margins.",
            "FOOD:\nBrisket\n\nBrisket is a cut of meat from the breast or lower chest of beef or veal. The beef brisket is one of the nine beef primal cuts, though the precise definition of the cut differs internationally. The brisket muscles include the superficial and deep pectorals. As cattle do not have collar bones, these muscles support about 60% of the body weight of standing or moving cattle. This requires a significant amount of connective tissue, so the resulting meat must be cooked correctly to tenderize the connective tissue.",
            "SPORTS:\nTable Tennis\n\nTable tennis, also known as ping-pong, is a sport in which two or four players hit a lightweight ball back and forth across a table using small rackets. The game takes place on a hard table divided by a net. Except for the initial serve, the rules are generally as follows: players must allow a ball played toward them to bounce one time on their side of the table, and must return it so that it bounces on the opposite side at least once. A point is scored when a player fails to return the ball within the rules. Play is fast and demands quick reactions. Spinning the ball alters its trajectory and limits an opponent's options, giving the hitter a great advantage.",
            "NATURE:\nWolf\n\nThe wolf (Canis lupus),[a] also known as the grey/gray wolf or timber wolf,[4][5] is a canine native to the wilderness and remote areas of Eurasia and North America. It is the largest extant member of its family, with males averaging 43–45 kg (95–99 lb) and females 36–38.5 kg (79–85 lb).[6] It is distinguished from other Canis species by its larger size and less pointed features, particularly on the ears and muzzle.[7] Its winter fur is long and bushy and predominantly a mottled gray in color, although nearly pure white, red and brown to black also occur.[5] Mammal Species of the World (3rd ed., 2005), a standard reference work in zoology, recognises 38 subspecies of C. lupus.[8]",
            "PEOPLE:\nVladimir K. Zworykin\n\nVladimir Kosmich Zworykin (Russian: Влади́мир Козьми́ч Зворы́кин, Vladimir Koz'mich Zvorykin; July 29 [O.S. July 17] 1888 – July 29, 1982)[1][2] was a Russian-born American inventor, engineer, and pioneer of television technology. Educated in Russia and in France, he spent most of his life in the United States. Zworykin invented a television transmitting and receiving system employing cathode ray tubes. He played a role in the practical development of television from the early thirties, including charge storage-type tubes, infrared image tubes and the electron microscope.[3]"

    };

    String[] urls = {
            "https://en.wikipedia.org/wiki/Chevrolet_LUV",
            "https://en.wikipedia.org/wiki/Emotion_Engine",
            "https://en.wikipedia.org/wiki/Mathematical_induction",
            "https://en.wikipedia.org/wiki/Brisket",
            "https://en.wikipedia.org/wiki/Table_tennis",
            "https://en.wikipedia.org/wiki/Wolf",
            "https://en.wikipedia.org/wiki/Vladimir_K._Zworykin"





    };

    CheckBox[] Boxes;

    Boolean[] BoxValues = {
            true,
            true,
            false,
            false,
            true,
            true,
            true,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_viewer);

        Boxes = new CheckBox[]{
                findViewById(R.id.cbAutomotive),
                findViewById(R.id.cbScience),
                findViewById(R.id.cbMath),
                findViewById(R.id.cbFood),
                findViewById(R.id.cbSports),
                findViewById(R.id.cbNature),
                findViewById(R.id.cbPeople),
        };

        textView = findViewById(R.id.textView);

        Panels = new String[MaxPanels];

        for(int i = 0; i<MaxPanels; i++){
            Boxes[i].setChecked(BoxValues[i]);
        }


        for(int i = 0; i<MaxPanels; i++){
            Panels[i] = hardcoded[i];
        }

        textView.setText(Panels[0]);

        Button learnMoreButton = findViewById((R.id.learnMore));

        learnMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Chrome = new Intent(Intent.ACTION_VIEW);
                Chrome.setData(Uri.parse(urls[currentPanel]));
                startActivity(Chrome);
            }
        });

        Button previousButton = findViewById(R.id.backButton2);
        Button nextButton = findViewById((R.id.nextButton));

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int offset = 0;
                boolean firstrun = true;
                while(Boxes[currentPanel + offset].isChecked() == false || firstrun)
                {
                    offset++;
                    if(currentPanel + offset > MaxPanels-1)
                        offset = -currentPanel;
                    firstrun = false;
                }
                currentPanel = (currentPanel+offset) % MaxPanels;
                textView.setText(Panels[currentPanel]);

            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int offset = 0;
                boolean firstrun = true;
                while(Boxes[currentPanel + offset].isChecked() == false || firstrun)
                {
                    offset--;
                    if(currentPanel + offset < 0)
                        offset = MaxPanels - 1 - currentPanel;
                    firstrun = false;
                }
                currentPanel = currentPanel + offset;
                textView.setText(Panels[currentPanel]);

            }
        });




        Button interestsButton = (Button) findViewById(R.id.Interests);
        interestsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScrollView scroll = (ScrollView) findViewById(R.id.InterestsScroll);
                int visibility = scroll.getVisibility();
                switch(visibility){
                    case View.VISIBLE:
                        scroll.setVisibility(View.INVISIBLE);
                        break;
                    case View.INVISIBLE:
                        scroll.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }

            }
        });

        Button logoutButton = (Button) findViewById(R.id.Logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainViewer.this, LoginActivity.class));

            }
        });
    }
}

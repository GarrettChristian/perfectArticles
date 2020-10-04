package com.perfectArticles.perfectArticlesBack.service.article;

import com.perfectArticles.perfectArticlesBack.domain.AddResponse;
import com.perfectArticles.perfectArticlesBack.domain.dto.ArticleDto;
import com.perfectArticles.perfectArticlesBack.domain.persistence.Article;
import com.perfectArticles.perfectArticlesBack.domain.persistence.Comment;
import com.perfectArticles.perfectArticlesBack.service.comment.CommentService;
import com.perfectArticles.perfectArticlesBack.util.ArticleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticlePersistenceService articlePersistenceService;

    public ArticleService(@Autowired CommentService commentService,
                          @Autowired ArticlePersistenceService articlePersistenceService) {
        this.commentService = commentService;
        this.articlePersistenceService = articlePersistenceService;
    }

    public AddResponse addArticleFromDto(ArticleDto articleDto) {

        AddResponse addResponse = new AddResponse().setMessage("Add article with comments");

        //Convert to Persistence object
        Article article = ArticleUtil.articleDtoToPersistence(articleDto);

        //Generate tags
        String text = article.getText();
        List<String> tags = ArticleUtil.generateArticleTags(text);

        if (CollectionUtils.isEmpty(tags)) {
            addResponse.updateMessage("No tags could be found for article ID: ");
        }
        article.setTags(tags);

        //Add article
        AddResponse addArticleAddResponse = articlePersistenceService.addArticle(article);
        addResponse.updateMessage(addArticleAddResponse.getMessage());

        return addResponse;

    }

    public AddResponse addArticleWithComments(Article article) {

        AddResponse addResponse = new AddResponse().setMessage("Add article with comments");

        //Generate tags
        String text = article.getText();
        List<String> tags = ArticleUtil.generateArticleTags(text);

        if (CollectionUtils.isEmpty(tags)) {
            addResponse.updateMessage("No tags could be found for article ID: ");
        }
        article.setTags(tags);

        //Add article
        AddResponse addArticleAddResponse = articlePersistenceService.addArticle(article);
        addResponse.updateMessage(addArticleAddResponse.getMessage());

        return addResponse;

    }

    public List<ArticleDto> getAllArticles() {

        List<Article> articles = articlePersistenceService.getAllArticles();

        List<ArticleDto> articleDtos = articles.stream()
                .map(ArticleUtil::articlePersistenceToDto)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return articleDtos;
    }

    public ArticleDto getArticleById(Integer id) {

        Article article = articlePersistenceService.getArticleById(id);

        return ArticleUtil.articlePersistenceToDto(article);
    }

    public List<ArticleDto> getRecentArticles(Integer amount) {

        Pageable pageable = PageRequest.of(0, amount, Sort.by("date").descending());

        Page<Article> articles = articlePersistenceService.getRecentArticles(pageable);

        List<ArticleDto> articleDtos = articles.stream()
                .map(ArticleUtil::articlePersistenceToDto)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return articleDtos;
    }

    public AddResponse loadTestArticles() {

        AddResponse addResponse = new AddResponse().setMessage("test data added").setSuccess(true);

        //https://www.newyorker.com/books/page-turner/the-difference-between-bird-watching-and-birding
        Article article1 = new Article().setAuthor("Jonathan Rosen")
                .setImageLocation("birdsEattingSeeds.jpg")
                .setTitle("Greatest Bird Picture Ever")
                .setImageCaption("Arlee took this picture of a bird")
                .setText("Birding is the opposite of being at the movies—you’re outside, not sitting in a windowless box; " +
                        "you’re stalking wild animals, not looking at pictures of them. You’re dependent on weather, " +
                        "geography, time of day—if you miss the prothonotary warbler, there isn’t a midnight showing. " +
                        "On the other hand, birding, like moviegoing, is at heart voyeuristic, and you can’t do it " +
                        "without technology—to bring birds closer you must interpose binoculars between yourself and " +
                        "the wild world. To find them in the wild, you need planes, trains, automobiles, and motorboats. " +
                        "Birds are natural; birders aren’t.\n\n" +
                        "And some birders are less natural than others, like the three characters at the heart of “The " +
                        "Big Year,” who are driven to see as many North American species as possible. They are genial " +
                        "caricatures of normal people, partly because they’re in a Hollywood movie, but mostly because " +
                        "they are birders. As a birder myself, I recognize the symptoms: I’ve travelled great distances " +
                        "to see birds; I’ve totted up the names of birds on lists and felt weirdly comforted, as if they " +
                        "guarded me against oblivion; I’ve listened, like Jack Black’s character, to birdcalls on my " +
                        "iPod. But I have to admit that at bottom I’m an indifferent birder, despite having written a " +
                        "book called “The Life of the Skies: Birding at the End of the Nature.” At the end of the day I " +
                        "am a bird-watcher, not a birder.\n\n" +
                        "This may seem like a pedantic distinction in an already marginal world, but it matters—though " +
                        "the two terms bleed into each other. Crudely put, bird-watchers look at birds; birders look " +
                        "for them. Ahab wasn’t fishing, and the guys in “The Big Year” aren’t watching birds, they’re " +
                        "scouring North America in a ruthless bid to tick off more species than anyone else. They don’t " +
                        "even have to see them—hearing their call is enough.\n\n" +
                        "Huge numbers of people are bird-watchers; the United States Fish and Wildlife Service " +
                        "estimates that something like forty-eight million Americans watch birds. Of those, only a " +
                        "tiny fraction have the time, money, and obsessive devotion for hardcore birding. But, " +
                        "inevitably, these are the guys who wind up in a movie, talking absurdly about being “the " +
                        "best birder in the world.”\n\n" +
                        "Thoreau, a bird-watcher, famously said that he could spend from dawn till noon sitting " +
                        "in his doorway surrounded by trees and birds; this may explain why there have not been " +
                        "many film adaptations of “Walden.” “The Big Year” has more in common with “The Canonball " +
                        "Run.” There’s a moment when Steve Martin, playing a high-powered businessman who thinks " +
                        "nothing of hiring a helicopter to spot Himalayan snowcocks in Nevada, is derided by " +
                        "another businessman for “bird watching.” “It’s called birding,” Martin says grimly, and " +
                        "if this were a Western, his gun-hand would twitch.\n\n" +
                        "Competitive cross-country birding didn’t really take hold until the nineteen-seventies. " +
                        "It’s a paradoxical fusion of countercultural, Earth Day, dropout rebellion merged with the " +
                        "world-conquering zeal of baby boomers. Birding is like competitive meditation.\n\n" +
                        "But our approach to the natural world has never been simple. Audubon kept boastful count " +
                        "of the birds he slaughtered, then he posed them in life-like positions and reanimated " +
                        "them in his paintings. In “The Big Year,” which is based on a book by Mark Obmascik, the " +
                        "character played by Owen Wilson fails to show up for a scheduled insemination of his wife " +
                        "because he gets a tip on a snowy owl, choosing to look at the natural world rather than be " +
                        "part of it. (In truth, Thoreau, living alone like a monk in the woods, wasn’t so different.)\n\n" +
                        "Like the hysterical paralytics Freud studied, birders reveal a great deal about universal " +
                        "human psychopathology, especially our tormented relationship to the natural world—the world " +
                        "that produced us and from which we are estranged. We’ve got to control nature, but if we " +
                        "control it too much we only wound ourselves.");

        Comment comment2 = new Comment().setDislikes(2)
                .setLikes(4)
                .setUserName("The Rock")
                .setText("incredible article I also think birds are thrilling")
                .setDate(new Date());

        Comment comment3 = new Comment().setDislikes(2)
                .setLikes(120)
                .setUserName("Greg Johnson")
                .setText("Binoculars are an essential tool for " +
                        "birders, but with so many models on the market, " +
                        "it can be daunting to find the perfect pair. It’s " +
                        "been three years since we first published our binoculars " +
                        "guide, so this summer we once again turned to professional ornithologists and" +
                        " dedicated birders to put a wide array of optics to the test. In July, dozens of attendees " +
                        "at the 2019 Audubon Convention descended upon a Milwaukee park to review nearly 50 pairs " +
                        "of binoculars from 16 companies under a range of conditions over three days.  ")
                .setDate(new Date());

        Comment comment4 = new Comment().setDislikes(2)
                .setLikes(4)
                .setUserName("Jay Blu")
                .setText("I would personally say cardinals are the best")
                .setDate(new Date());

        article1.addComment(comment2);
        article1.addComment(comment3);
        article1.addComment(comment4);

        AddResponse addResponse1 = addArticleWithComments(article1);
        addResponse.updateMessage(addResponse1.getMessage());

//        https://pitchfork.com/reviews/albums/harry-styles-fine-line/
        Article article2 = new Article().setAuthor("Jeremy D. Larson")
                .setImageLocation("harryStylesAlbum.jpg")
                .setTitle("The Burdens of Being a Star")
                .setImageCaption("Harry styles new album")
                .setText("In a Rolling Stone profile earlier this year, Harry Styles recalled how he kept watching " +
                        "this interview with David Bowie on his phone for inspiration. In the clip, Bowie offers this " +
                        "chestnut about creativity: \"Always go a little further into the water than you feel you are " +
                        "capable of being in. Go a little bit out of your depth. When you don’t feel that your feet are " +
                        "quite touching the bottom, you’re just about in the right place to do something exciting.\"" +
                        "\n\n Styles was invoking his own artistic process, illuminating the lengths to which he hoped " +
                        "to travel on his second solo album, Fine Line. He was also demonstrating the invincible " +
                        "oblivion of even our most charming pop stars. For Styles, Fine Line is the sound of an " +
                        "artist plumbing the abyss. For us, it’s the sound of a celebrity sticking his toes in " +
                        "the sand. It’s ostensibly his freedom record, one that indulges his every musical and " +
                        "psychedelic whim. It is also removed enough from One Direction to finally not be judged " +
                        "in relationship to them (unlike his spare and often lovely self-titled debut from 2017). " +
                        "By corralling a new flock of influences—from ’70s power pop and Laurel Canyon folk-rock to " +
                        "the sort-of soul of Coldplay—Styles showcases his gift for making music that sounds like " +
                        "good music.\n\n" +
                        "Which is to say the actual sound of Fine Line is incredible, and most songs have at " +
                        "least one great moment to grab hold of: the clusters of background harmonies on “Golden,” " +
                        "the synth sweeps throughout “Sunflower, Vol. 6,” the strange and alluring pre-chorus " +
                        "on “Lights Up,” a song that embodies Styles’ fluorescent charm, his swagger, his desire " +
                        "to be both weird and adored. He has talked recently about his fear of making music after " +
                        "he left One Direction, the pressure of finding a radio single. But to hear him sing " +
                        "sun-warmed, folk-tinged acoustic rock backed by only a handful of musicians is refreshing. " +
                        "There were easier, more callow roads for him to take.\n\n" +
                        "While the music wades into the mystic, his songwriting, pointedly, does not. Fine Line, " +
                        "in part, deals with Styles’ breakup with the French model Camille Rowe, but he renders " +
                        "their romance in the primary colors of needing you, missing you, and remembering you. " +
                        "Emotional beats rise and fall with all the stakes of a refill on a glass of water. Styles " +
                        "doesn’t have the imagination of Bowie or another pop-rock touchpoint here, Fleetwood Mac, " +
                        "who took their lives and transfigured them through cosmic fantasia or Victorian grandeur. " +
                        "Styles’ attempts at this mode worked slightly better on his more austere debut, but in this " +
                        "rainbow-parade of psychedelic pop, the dullness is cast into sharp relief.\n\n" +
                        "The same Styles who sang the unforgettable line, “Even my phone misses your call, by the way” " +
                        "just one album ago, can’t muster a memorable flourish, a vivid image, or the same diaristic " +
                        "self-dramatizing wink as Taylor Swift. Instead, feet firmly planted on the shore, Styles " +
                        "simply summarizes and apologizes and reflects as if he were just telling this story to his " +
                        "mates. During the stretch of ballads that comprise the middle third of the album, he " +
                        "sings, “I’m just an arrogant son of a bitch who can’t admit when he’s sorry,” and, “What if " +
                        "I’m someone I don’t want around?” What these earnest text messages reveal about Styles is that " +
                        "he has a desire to do right, to be a good person, or at least to be seen as one. And " +
                        "that’s it—we remain no closer to understanding him as a songwriter or solo artist.\n\n" +
                        "The musicians here—including songwriters Kid Harpoon and Jeff Bhasker, producer and " +
                        "multi-instrumentalist Tyler Johnson, guitarist Mitch Rowland, among others—summon a " +
                        "retro live-band sound, no producer tags, no chart-storming aesthetics. But Styles " +
                        "treats them more like a collection of instruments than an actual band, which makes the " +
                        "anonymous two-minute guitar solo at the end of “She” seem pretty meaningless on a Harry " +
                        "Styles solo record. Even more infuriating is “Treat People With Kindness,” an awful " +
                        "chimera of Jesus Christ Superstar and Edgar Winter Group’s “Free Ride” that confuses " +
                        "hand-claps with happiness. Each song is a new outfit for Styles, hoping one will carry " +
                        "his reality-competition voice and illuminate his reality-competition lyrics.\n\n" +
                        "There are glimpses, like in “Canyon Moon,” of the sort of intimate connection " +
                        "Styles hopes to forge. It’s one of those running-with-a-kite-down-a-grassy-hill songs, " +
                        "covered in ringing acoustic guitars that evoke his bright smile. “Cherry” rises out of the" +
                        " cliche and into something darker and lasting and Swiftian: “I noticed that there’s a " +
                        "piece of you in how I dress/Take it as a compliment.” Styles is here, buried underneath the " +
                        "fame and the fear. I hear his sweetness, his charm, his elegance. But mostly I hear a guy " +
                        "who’s still afraid he’ll never make a David Bowie record.");

        for (int i = 0; i < 111; i++) {
            Comment comment22 = new Comment().setLikes(i + 100)
                    .setDislikes(i + 1)
                    .setUserName("Commenter: #" + (i + 1))
                    .setText("Music is thrilling " + i)
                    .setDate(new Date());

            article2.addComment(comment22);
        }

        AddResponse addResponse2 = addArticleWithComments(article2);
        addResponse.updateMessage(addResponse2.getMessage());

        //https://www.shutupandsitdown.com/review-sagrada/
        Article article3 = new Article().setAuthor("Paul Dean")
                .setImageLocation("SagradaImage.jpg")
                .setTitle("Review: Sagrada")
                .setImageCaption("Sagrada, box and board")
                .setText("I keep telling people that I’m not especially enamoured with Sagrada, that it’s just my " +
                        "latest diversion, but then I say they should still try a game with me. Then it hits the table " +
                        "again. Then I’m playing it once more. Then we have a good time and I think about the next " +
                        "person I want to try it with. Then it goes back into my bag and I bring it to someone new.\n\n" +
                        "Am I in denial about just how much I like this?\n\n" +
                        "There’s something gloriously decadent about a huge bag of dice. When I was young, I had a " +
                        "handful of board games and I was lucky if the dice they came with were still in the box. " +
                        "Having even a single die, one little agent of randomness, felt a little bit exciting. Some " +
                        "feelings never quite go away and, even after playing hundreds of board games, Sagrada’s " +
                        "ninety holy, gittering cubes still seem like a mountain of candy to a boy who has barely " +
                        "tasted sugar.\n\n" +
                        "So you understand how I felt when I opened this box and found everything was all about " +
                        "those dice, about rolling and choosing and arranging and ordering them. Sagrada is " +
                        "ostensibly a game of methodically making stained glass windows according to particular " +
                        "patterns, but really it’s just has you lining up dice according to three criteria, a task " +
                        "initially as easy as counting your own nose, but eventually as furiously impossible as " +
                        "sorting confetti in a hurricane.\n\n" +
                        "Each player begins by selecting a window pattern, then slotting that pattern into the " +
                        "cardboard window frame that is their miniature player board. These patterns all have " +
                        "suitably sacerdotal names like “Lux Mundi”* or “Firmitas”** or “Gravitas”*** and they " +
                        "lay out the famous First Rule of Dice Club. The First Rule of Dice Club is that you " +
                        "gotta place dice, which represent your coloured panes of glass, according to these " +
                        "patterns. A five upon a space calling for a five. A yellow upon a space praying for a " +
                        "yellow. No compromises. The cards are gospel.\n\n" +
                        "Each card has a difficulty rating. The harder the card, the more favour tokens it " +
                        "blesses you with. These are indulgences that, even if they don’t save your soul, may still " +
                        "save your work. At the start of the game, three randomly selected tool cards are available to " +
                        "everyone and each of these allows you to modify your window (well, your dice) in some way. " +
                        "Using these involves paying favour tokens, so the tougher your project, the more " +
                        "opportunity you’re given to modify it.\n\n" +
                        "Back to those tools later, first we have to get dice drafting. Each of the game’s ten " +
                        "turns has you searching for your salvation in a giant black bag, pulling out handfuls of " +
                        "dice relative to the number of players and then rolling them all. Everyone takes turns " +
                        "choosing one die at a time from this pool and, beginning at one corner of their window, starts " +
                        "to build out their pattern.\n\n" +
                        "This is a good time to learn about the Second Rule of Dice Club. The Second Rule of Dice " +
                        "Club is that dice of the same colour may never, ever be placed next to one another. " +
                        "It is a sin. Diagonally adjacent is acceptable, but edges can never touch. That’s okay, though," +
                        " because it only means you’re going to be assembling an even more divinely diverse rainbow of " +
                        "colour, right? Well, maybe. It mostly means that everything ends up looking incredibly " +
                        "disordered, something between a Skittle spillage and a unicorn autopsy.\n\n" +
                        "So you’re drawing dice and rolling dice and swiping dice, all through a rotating " +
                        "player order that means you’ll get first choice at least some of the time. But when you " +
                        "don’t? Well, it’s not so bad, because there are so many dice and so many spaces that you " +
                        "can usually put something somewhere. (I believe it was St. Luke who said “Meh. A red is as " +
                        "good as a purple.”) You’re not going to make a particularly pretty picture, but you’ll at " +
                        "least avoid the cosmic horror, the eternal damnation that is two yellows touching.\n\n" +
                        "Easy. Placing dice is easy.\n\n" +
                        "Or is it? You’re also keeping an eye on the victory criteria. Each time you play Sagrada, " +
                        "deliverance comes from three different public cards that task you to, say, make a column " +
                        "of dice that are all different colours, or use as many threes and fours as possible. You " +
                        "have a private victory condition too, awarding you points according to how much you use a " +
                        "certain colour. Dropping dice is all well and good, but satisfying these terms? That’s " +
                        "much trickier.\n\n" +
                        "I’m ahead of myself. I haven’t told you about The Third Rule of Dice Club. The Third Rule " +
                        "of Dice Club is that dice showing the same value also cannot be placed next to one another. " +
                        "Dice value represents shade (colour, not attitude) and while two different shades may meet " +
                        "in the next life, they sure won’t here. As I’m sure you can guess, finding a die of a " +
                        "different colour and value to place next to one or two others is fine, but when you have " +
                        "a an almost complete window, shining with shade and colour, the holes you need to fill are " +
                        "MADDENINGLY SPECIFIC and the dice you roll PURITANICALLY UNCOMPROMISING.\n\n" +
                        "Somehow, this is brilliant.\n\n" +
                        "You can see it coming. Sometimes you walk right into it, not even realising you dropped " +
                        "a red die right next to a space where your pattern also demands a red, but other times you " +
                        "know that, very soon, you’ll need a specific colour and number to follow another specific " +
                        "colour and number. Sagrada would be much easier if you could drop dice anywhere, but remember, " +
                        "scripture states that you always work out from that single corner, space by space, " +
                        "narrowing your scope.\n\n" +
                        "This gives the game a natural arc from the dinky to the diabolical, your first turns all " +
                        "boasts about how simple it is, your last full of curses to the heavens. It’s there that " +
                        "you really burn your favour tokens as you try to hammer and chisel your way out of whatever " +
                        "mess you’ve latticed your way into, but it’s a bad time when you discover that the first person " +
                        "using a tool only pays one token. Everyone after must spend two. Those tokens won’t last " +
                        "long and you won’t be using tools often, something of a shame as they’re the subtle " +
                        "seasoning that can add so much flavour.\n\n" +
                        "I’ve said almost nothing about how you interact with your fellow players. While Sagrada " +
                        "is an ever-evolving puzzle that gets harder and harder, it always remains your puzzle, a " +
                        "private puzzle too sacred for others to touch. Though you can never covet thy neighbour’s " +
                        "dice, you can at least is deny them first, which is where this becomes a particularly good " +
                        "game for just two.\n\n" +
                        "In a larger game, you’re almost always more rewarded by, and more invested in, making good " +
                        "choices for yourself than you are trying to crack everyone else’s glasswork, but in a " +
                        "face-off, there’s a finer balance between simply taking what benefits you and trying to " +
                        "steal away dice that you know could really help your opponent. Especially with the smaller " +
                        "dice pool and especially mid-game, where it’s increasingly tempting to compromise on what you " +
                        "most want just to make a devilish, debilitating denial.\n\n" +
                        "For some of you, this may also be where the insults start. Where you rage because someone took " +
                        "a green five when they would’ve been fine with any other five. And all you needed was any " +
                        "green. Any green at all. You weren’t even going to be fussy about it. That rising " +
                        "frustration you begin to feel? Yeah, you can spread that. Go on. Do it.\n\n" +
                        "Perfectly completing your window is possible, though hardly essential. The odd hole is " +
                        "more than compensated for by whatever scoring criteria you’ve fulfilled and, after a " +
                        "couple of games, you realise that Sagrada isn’t just about the luck of the dice and a few " +
                        "canny arrangements. It really does reward planning, foresight and, in a two-player " +
                        "situation, knowing when to be a jerk.\n\n" +
                        "That said, each game is going to feel similar to the last, and while there’s a selection of " +
                        "different patterns and victory conditions, these don’t change either the tactics or the " +
                        "arc of the game. The tools are the best way to mix things up and having only three of these " +
                        "available each time actually ends up feeling like a limitation. I constantly wanted to be " +
                        "using more of these, more often, to be both creative and disruptive.\n\n" +
                        "Is it odd, too, for me to say that I would have liked Sagrada more if I’d ended up creating " +
                        "patterns that were prettier, more distinct and perhaps somehow scored on their aesthetics? " +
                        "I know, I might be asking too much, but to work so hard arranging my dice and end up with " +
                        "something that looks more like a Pollock than a Pozzo? I’d like the payoff to be more than " +
                        "pure points.\n\n" +
                        "And that’s the trick. I keep wanting just a little more from Sagrada, which is why I’m not " +
                        "leaping forward to recommend it, not petitioning for its sainthood. It’s going to be far too " +
                        "simple for some players, but that won’t stop it being a wonderful little puzzle to others, m" +
                        "yself included. While it doesn’t offer as much variety to it as I’d like, it remains ever-" +
                        "vexing and I’ve had a ton of fun playing it, watching people twist in torment as the game gets " +
                        "tougher and tougher. I know I’m going to put it to one side soon but… no. Not just yet.");

        AddResponse addResponse3 = addArticleWithComments(article3);
        addResponse.updateMessage(addResponse3.getMessage());


        //
        Article article4 = new Article().setAuthor("Garrett Christian")
                .setImageLocation("meAndQuinnAtBeach.jpg")
                .setTitle("Garrett Christian")
                .setImageCaption("Garrett (left) and Quinn (right) at the beach")
                .setText("Garrett Christian is very excited to present his project again to Brightspot. " +
                        "Thank you for having me! (the rest of the text is just the first paragraphs of strawberries " +
                        "wikipedia page)\n\n" +
                        "The garden strawberry (or simply strawberry; Fragaria × ananassa)[1] is a widely grown hybrid " +
                        "species of the genus Fragaria, collectively known as the strawberries, which are " +
                        "cultivated worldwide for their fruit. The fruit is widely appreciated for its characteristic " +
                        "aroma, bright red color, juicy texture, and sweetness. It is consumed in large quantities, " +
                        "either fresh or in such prepared foods as jam, juice, pies, ice cream, milkshakes, and chocolates. " +
                        "Artificial strawberry flavorings and aromas are also widely used in products such as candy, " +
                        "soap, lip gloss, perfume, and many others.\n" +
                        "\n" +
                        "The garden strawberry was first bred in Brittany, France, in the 1750s via a cross of " +
                        "Fragaria virginiana from eastern North America and Fragaria chiloensis, which was brought" +
                        " from Chile by Amédée-François Frézier in 1714.[2] Cultivars of Fragaria × ananassa have" +
                        " replaced, in commercial production, the woodland strawberry (Fragaria vesca), which was " +
                        "the first strawberry species cultivated in the early 17th century.[3]\n" +
                        "\n" +
                        "The strawberry is not, from a botanical point of view, a berry. Technically, it is an " +
                        "aggregate accessory fruit, meaning that the fleshy part is derived not from the plant's " +
                        "ovaries but from the receptacle that holds the ovaries.[4] Each apparent \"seed\" (achene)" +
                        " on the outside of the fruit is actually one of the ovaries of the flower, with a seed " +
                        "inside it.[4]\n" +
                        "\n" +
                        "In 2017, world production of strawberries was 9.2 million tonnes, led by China with " +
                        "40% of the total.");

        AddResponse addResponse4 = addArticleWithComments(article4);
        addResponse.updateMessage(addResponse4.getMessage());

        //https://thethoughtfulgamer.com/2017/06/02/viticulture-essential-edition-review/
        Article article5 = new Article().setAuthor("Adam Chutuape")
                .setImageLocation("viticulture.jpg")
                .setTitle("Viticulture Review")
                .setImageCaption("Essential Edition of Viticulture")
                .setText("Viticulture: Essential Edition is one of the most polished, professional board game packages " +
                        "I’ve ever seen. From the perfectly sized box to the phenomenal graphic design to the most succ" +
                        "inct and clear rulebook I have ever read, Stonemaier games have gone above and beyond to provi" +
                        "de a quality product.\n" +
                        "\n" +
                        "Beyond the beautiful presentation lies a fantastic worker placement game that is a lot more in" +
                        "teresting than its closest popular peers–Stone Age and Lords of Waterdeep. While perhaps a ha" +
                        "lf-step more complicated than those two games, Viticulture has the advantage of being more t" +
                        "hematically cohesive.\n" +
                        "\n" +
                        "Gears of [Pinot] Noir\n" +
                        "Let me explain. As the name implies, Viticulture is a game about winemaking. The player boa" +
                        "rd contains three major sections: the fields, the crush pads, and the wine cellar. On the c" +
                        "entral board, there are a number of different actions available to the players, five of whi" +
                        "ch are central to the winemaking process. First, you acquire vines for your field. These vi" +
                        "nes are represented by cards which have different grapes actually used in Tuscany (Sangiov" +
                        "ese, Malvasia, Pinot Nero, etc). Second, you must plant those vines in your field. Third, " +
                        "you must harvest the grapes, which sends tokens to your crush pad. The fourth key action i" +
                        "s making the wine itself, which transfers the tokens from the crush pad to the wine cellar" +
                        ". Finally, you can sell the wines you have in the cellar.\n" +
                        "\n" +
                        "Every other action space on the main board is designed to help you accomplish those five t" +
                        "asks. You can give tours of the winery to make money, add buildings to help your operatio" +
                        "n grow, draw additional grape or wine order cards, or play visitor cards. These action spa" +
                        "ces are split into two “seasons”, so you have to consider how you want to split up your wo" +
                        "rkers between them each round.\n" +
                        "\n" +
                        "The visitor cards, which are essentially action cards, are one of the most interesting asp" +
                        "ects of the game. Usually with this kind of Euro-style game if there is an action card mecha" +
                        "nism, the cards will only apply marginal or situational benefits.\n" +
                        "\n" +
                        "In Viticulture, every single action card is significant. From what I understand, part of t" +
                        "he upgrade with the Essential Edition was a reworking of the visitor cards to make them use" +
                        "ful in any phase of the game. Every time you draw a visitor card, it can potentially be the " +
                        "key you need to push toward a more aggressive strategy, or move you ahead one step further i" +
                        "n the winemaking process this round, or give you an action that lets you sidestep an action s" +
                        "pace blocked by other players. I have never played a Euro-style game where action cards are s" +
                        "o game-changing, and it’s a great feature. It creates a push your luck dynamic, where you can" +
                        " risk going for more visitor cards at the expense of other pieces of the puzzle, because the" +
                        "re’s a decent chance that the card is going to give you an even more efficient way to accomp" +
                        "lish the same goal.\n" +
                        "\n" +
                        "The downside, of course, is that if someone happens to get a string of visitor cards that wo" +
                        "rk particularly well together, it can propel them into the lead. In one game I played, one " +
                        "of my opponents got 2 cards that cheaply and easily let him acquire additional workers. By" +
                        " the end of the very first round he had doubled his worker pool from the starting 3 to the" +
                        " maximum of 6. The rest of us were left behind with 3-4 workers and he managed to build up " +
                        "a winemaking engine significantly quicker than us. By the midway point of the game, there w" +
                        "as no chance that anyone else was going to catch up to him. In another game, I happened to " +
                        "draw visitor cards that always seemed to be poorly timed. While the designers have done a" +
                        " fantastic job making the visitor cards as useful as possible in any situation, luck can st" +
                        "ill be a significant factor.\n" +
                        "\n" +
                        "Luck can also play a significant factor when it comes to the wine order cards. They dicta" +
                        "te your winemaking strategy as they are the only way to get victory points through selli" +
                        "ng wines. If you happen to draw a good mix of early game and late game focused wine orde" +
                        "r cards at the beginning, you can probably go throughout the game without drawing any a" +
                        "dditional cards, saving 2-3 actions. If you do not get a good mix of cards (for instance" +
                        ", getting 3 orders that only want red wine), you get a difficult decision: pursue a sub-op" +
                        "timal strategic path dictated by your cards, or spend additional actions to try to draw be" +
                        "tter, more balanced orders. This does not seem as variable as the worker cards, but I beli" +
                        "eve it bears mentioning.\n" +
                        "\n" +
                        "Complex and Spicy\n" +
                        "Fortunately, the game provides a couple of clever ways to mitigate the common areas of b" +
                        "ad luck in worker placement games. The most common frustration with this genre is an opp" +
                        "onent blocking a critical action on the main board, robbing you of the final push you ne" +
                        "ed to achieve victory or severely delaying your economic engine.\n" +
                        "\n" +
                        "Viticulture mitigates this frustration in three small ways. First, everyone gets o" +
                        "ne “grande” worker, which can be placed on any action space, regardless if it has a" +
                        "lready been taken. Second. the aforementioned visitor cards frequently give you alternat" +
                        "ive means of accomplishing the actions on the board. Third, the turn order is determined through" +
                        " a small mini-round, so if you need to go toward the front of the turn order, you frequently" +
                        " have that option.\n" +
                        "\n" +
                        "Grande workers are frequently seen in packs.\n" +
                        "This clever mechanism involves 7 spaces on the side of the board that determine turn order. " +
                        "There is a first player token that moves around the board as in most games, but that only de" +
                        "termines who selects first on the turn order track. Every space except for the first has an" +
                        " associated benefit. As you go further down the track, the benefits become more and more en" +
                        "ticing. They might simply give you a single coin, an extra visitor card, or even an addit" +
                        "ional worker for that round.\n" +
                        "\n" +
                        "The turn order track is a great addition to the game and is certainly a better solution" +
                        " than first player rotating around the table each round. However, in most of my games there" +
                        " was a very significant advantage to being able to grab the first spot in the last round. " +
                        "The game ends at the end of the round in which someone has hit 20 victory points, so you w" +
                        "ill know almost certainly which round is going to be the last. In that round, the “harvest g" +
                        "rapes”, “make wine”, and “fulfil wine order” spots are going to be in incredibly high demand" +
                        ". If you need to do two or more of those actions and you are picking 3rd or 4th for turn orde" +
                        "r, you will probably not be able to accomplish those two actions without the aid of a visito" +
                        "r card. In that sense, turn order problems are mitigated but not eliminated. I think this met" +
                        "hod is probably also better than the Agriciola/Lords of Waterdeep method of having a particu" +
                        "lar action space that grants you first player, but I am not completely sold yet. You will hav" +
                        "e games where, due to the where the first player token is, you will know you have no chance " +
                        "of winning going into the final round.\n" +
                        "\n" +
                        "Despite my reservations above, Viticulture is an incredibly pleasant and fun game. The pres" +
                        "entation is stellar and you can’t help but have a good time seeing your little wine pro" +
                        "duction system play out. Even if it doesn’t have the strategic depth or refined balance of" +
                        " its more serious worker placement peers, a 4-5 player game with people who know the rules " +
                        "is only going to take a bit over an hour, so if you feel like you get robbed by one of t" +
                        "he luck elements, it doesn’t feel like you’ve wasted your time.\n" +
                        "\n" +
                        "The Essential Edition version adds a pinch of flavor by including “Mamas and Papas” cards" +
                        " which provide variable starting resources. It also comes with a solo variant which is si" +
                        "mple and enjoyable. Every mechanism in the game feels like it was designed and crafted w" +
                        "ith care and ingenuity.\n" +
                        "\n" +
                        "One final note of interest is that the player count can dramatically affect the feel of" +
                        " the game. Every action space on the board has three spaces. One space is available in" +
                        " 1-2 player games, two spaces are available with 3-4 players, and all three spaces are u" +
                        "sed with 5-6 player games. I’ve not yet played with 6 players, but the 2 and 4 player ga" +
                        "mes feel significantly more cutthroat and tight compared to the 3 and 5 player games. " +
                        "I’m not sure if I count this as a positive or a negative, because while it can be a" +
                        "nnoying to have the feel of the game change so significantly by player count, it al" +
                        "so provides two unique playing experiences. Neither of them I feel are better than " +
                        "the other–they’re just different.\n" +
                        "\n" +
                        "I love how the glass wine tokens magnify the image underneath.\n" +
                        "A Fine Vintage\n" +
                        "To me, this is the perfect introduction to worker placement or euro games. It has" +
                        " a confidence of presentation and design that makes it welcoming, and thematically" +
                        " sound mechanisms that just make sense, so you can teach the game with the language" +
                        " of the theme instead of the language of board games. The process of playing the ga" +
                        "me in and of itself feels rewarding regardless of how well you do. It’s short enoug" +
                        "h that trying out alternative strategies doesn’t feel like a waste of time, and that" +
                        " final round where multiple people are trying to barely edge out each other on the v" +
                        "ictory point track is exciting.\n" +
                        "\n" +
                        "Viticulture: Essential Edition is aptly named. I hesitate to call any board game e" +
                        "ssential because that seems hyperbolic–no game is going to be good for every player" +
                        ". But if there’s a worker placement game I’ve played that deserves to be considered f" +
                        "or every gamer’s collection, it’s this one.");

        AddResponse addResponse5 = addArticleWithComments(article5);
        addResponse.updateMessage(addResponse5.getMessage());

        Comment comment224 = new Comment().setDislikes(2)
                .setLikes(4)
                .setUserName("The Rock")
                .setText("incredible article I enjoyed this!")
                .setDate(new Date());

        Comment comment33 = new Comment().setDislikes(2)
                .setLikes(120)
                .setUserName("Greg Johnson")
                .setText("My biggest gripe with the game as a winemaker is that leaving fruit out for a year should " +
                        "not increase the quality of the wine... you have to throw that out. Other than that " +
                        "its a pretty good game")
                .setDate(new Date());

        Comment comment44 = new Comment().setDislikes(2)
                .setLikes(4)
                .setUserName("Boar Gam")
                .setText("Fantastic worker placement game with some card play and a unique wine aging mechanic. " +
                        "The production is top notch and the theme is very approachable and engaging. I would say " +
                        "yes, it is really that good.")
                .setDate(new Date());

        article5.addComment(comment224);
        article5.addComment(comment33);
        article5.addComment(comment44);

        //https://thirstkey.com/kota-the-friend-everything-album-review/#:~:text=%E2%80%9C
        // Everything%E2%80%9D%20is%20a%20very%20necessary,minutes%2C%20but%20a%20worthwhile%20one.
        Article article6 = new Article().setAuthor("Julian Veich")
                .setImageLocation("kotaEverything.jpg")
                .setTitle("Everything Album Review")
                .setImageCaption("Cover of Kota The Friend's album Everything")
                .setText("Going into “Everything“, I had only heard a handful of Kota The Friend’s songs.\n" +
                        "\n" +
                        "I’ve always heard his name praised and his back catalog definitely warrants a listen. " +
                        "So, I decided to use his new album “Everything” as my proper introduction.\n" +
                        "\n" +
                        "My ideas on what to expect weren’t very developed being based on impressions from just a " +
                        "couple of songs, but I was excited to see what everyone’s been talking about.\n" +
                        "\n" +
                        "So let’s dive in.\n" +
                        "\n" +
                        "Kota stated multiple times that the intention with “Everything” was to make a noticeably " +
                        "happier album. Which he definitely does. But in doing so, whether intentionally or not, what" +
                        " he creates is something very nostalgic. The production, done by Kota himself, definitely " +
                        "boosts that feeling. But it’s really in the lyrics. The subtle details he chooses to include " +
                        "that made me feel like I was a part of what he was rapping about. At the very least, I could " +
                        "envision it clearly.\n" +
                        "\n" +
                        "Usually, I need at least 2 years removed from an album to consider it even slightly nostalgic " +
                        "but “Everything” carries that feeling sheerly in its presentation. The first two tracks " +
                        "“Summerhouse” and “Mi Casa” immediately set that tone. They’re both very laid back songs yet " +
                        "Kota does demonstrate his ability to flow on them. Both tracks have very strong second verse " +
                        "performances from Kota. Once he gets comfortable on these beats, he really takes off. I think " +
                        "a big part of that is because he produces everything himself. (He only has assistance on a " +
                        "couple of songs)\n" +
                        "\n" +
                        "The album’s strongest moment in terms of flow and production for me is “B.Q.E.”. The horns on t" +
                        "he beat are gorgeous and addictive. Kota, Joey Bada$$, and Bas all pay homage to their home of" +
                        " New York with quick, infectious flows. Everybody is in their element here and it makes for a " +
                        "pleasantly surprising grouping of talented rappers.\n" +
                        "\n" +
                        "“Long Beach” is the poppiest song on the album and Kota pulls it off pretty well. It " +
                        "reminds me a lot of Goldlink’s more dance-oriented cuts. While I probably wouldn’t have " +
                        "enjoyed multiple songs like this on the album, I do enjoy this one. If the album’s goal is " +
                        "positivity, it makes sense to have at least one moment where you want to dance.\n" +
                        "\n" +
                        "While “Everything” is far from a concept record, I still have to praise its brief uses of " +
                        "skits. The short appearances from Lupita Nyong’o and Lakeith Stanfield defining what means " +
                        "everything to them stay on the album’s theme without taking too much time away from the music. " +
                        "I’ve criticized Royce Da 5’9 and Joyner Lucas for the skits on their albums but I finally " +
                        "have an example in 2020 of skits being well placed and executed. Thank you, Kota.\n" +
                        "\n" +
                        "“Away Park” is a VERY close second for my favorite track. It’s that nostalgia factor " +
                        "I mentioned this album has encapsulated and beautifully orchestrated in one song. Kota’s " +
                        "subtle storytelling is at it’s best in the verses and the beat is fittingly understated yet " +
                        "so smooth. The singing at the end from Kaiit alone just FEELS like summer. The whole song is " +
                        "so warm and lackadaisical. It makes me so happy.\n" +
                        "\n" +
                        "After hitting such a high, it saddens me that “Volvo” grinds the album’s flow to almost a " +
                        "halt. Kota’s flow is so dull and monotonous on the song. It feels egregiously out of place " +
                        "ere. Not that I’d enjoy it much in any context. The lyrics don’t do anything to save it eith" +
                        "er. Along with feeling low energy, it feels low effort. It’s the only song I have such comp" +
                        "laints about and that’s the biggest reason it’s such a questionable inclusion. Like the album" +
                        " as a whole though, it comes and goes quickly.\n" +
                        "\n" +
                        "“Morocco” is the only other disappointing song but not because of Kota. Kota’s verse on the " +
                        "song is actually one of my favorites he lays down. His humor and confidence really shine. Bu" +
                        "t the feature from tobi lou is written and delivered like a bad freestyle. The rhymes are ba" +
                        "sic at best and their delivery isn’t any better. It’s a short verse but it really did kill m" +
                        "y enjoyment of the song. Like with “Volvo”, I think every other feature on this album was gr" +
                        "eat. Which makes this one a head-scratcher.\n" +
                        "\n" +
                        "“Seven (Interlude)” does have a great verse from Kota. I really like his rhyme scheme there" +
                        ". But the interlude is really more of a platform for Kota’s spoken section at the end. He s" +
                        "hares his philosophies since becoming a father and basically presents to us the foundation o" +
                        "f the album’s conception. I really like where Kota’s coming from here. As good as he is at r" +
                        "apping, it was nice to hear him directly articulate himself and his views. It feels a little " +
                        "more personal.\n" +
                        "\n" +
                        "There are few better feature choices for an album about happiness than KYLE. After not heari" +
                        "ng him in a long time, he really came through on “Always”. He hasn’t missed a beat. He still " +
                        "delivers honest bars with a sense of humor and a little self-deprecation. His creative re-pur" +
                        "posing of Biggie’s “Notorious Thugs” flow is deserving of some praise, too. Kota delivers " +
                        "two solid verses so as to not get outshined. His presence is still very much felt in the song.\n" +
                        "\n" +
                        "Particularly in his last few lines: “Every day I’m findin’ somethin’ different that I’m liv" +
                        "in’ for. Tryna tell my people they don’t gotta keep they ceilin’ low. Just keep your spirits" +
                        " high, keep on movin’ as you heal and grow.”\n" +
                        "\n" +
                        "The title track and album closer doesn’t overextend itself but it’s another dose of calming " +
                        "imagery to walk us out of the album. Kota sounds content with the present as he revisits the" +
                        " past. The ending with him and his son is about as wholesome as it gets. It does what it nee" +
                        "ds to do. Nothing less, nothing more.\n" +
                        "\n" +
                        "“Everything” is a very necessary album for the moment. It feels like summer in a time when " +
                        "nothing else in the world really does. It’s not a philosophical record but I still see " +
                        "it as a spiritual cleanser purely through sound. It’s a short trip, clocking in at around " +
                        "35 minutes, but a worthwhile one. Despite it not being as consistent as I wish it were." +
                        "Nevertheless, Kota The Friend is clearly very talented and I owe it to myself to check out " +
                        "his previous work as I await what he does next.");

        for (int i = 0; i < 111; i++) {
            Comment comment222 = new Comment().setLikes(i + 22)
                    .setDislikes(i + 1)
                    .setUserName("Commenter: #" + (i + 1))
                    .setText("Music is thrilling " + i)
                    .setDate(new Date());

            article6.addComment(comment222);
        }

        AddResponse addResponse6 = addArticleWithComments(article6);
        addResponse.updateMessage(addResponse6.getMessage());

        return addResponse;

    }

}

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

        return addResponse;

    }

}

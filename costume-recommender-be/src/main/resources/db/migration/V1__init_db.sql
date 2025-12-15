CREATE TABLE scare_level(
                           id INT AUTO_INCREMENT,
                           name VARCHAR(50) UNIQUE NOT NULL,
                           PRIMARY KEY(id)
);

CREATE TABLE budget(
                       id INT AUTO_INCREMENT,
                       name VARCHAR(50) UNIQUE NOT NULL,
                       PRIMARY KEY(id)
);

CREATE TABLE gender(
                       id INT AUTO_INCREMENT,
                       name VARCHAR(50) UNIQUE NOT NULL,
                       PRIMARY KEY(id)
);

CREATE TABLE age(
                    id INT AUTO_INCREMENT,
                    name VARCHAR(50) UNIQUE NOT NULL,
                    years_from INT,
                    years_to INT NOT NULL,
                    PRIMARY KEY(id)
);

CREATE TABLE group_type(
                          id INT AUTO_INCREMENT,
                          name VARCHAR(50) UNIQUE NOT NULL,
                          PRIMARY KEY(id)
);

CREATE TABLE time_prep(
                         id INT AUTO_INCREMENT,
                         name VARCHAR(50) UNIQUE NOT NULL,
                         PRIMARY KEY(id)
);

CREATE TABLE theme(
                      id INT AUTO_INCREMENT,
                      name VARCHAR(100) UNIQUE NOT NULL,
                      PRIMARY KEY(id)
);

CREATE TABLE color(
                      id INT AUTO_INCREMENT,
                      name VARCHAR(50) UNIQUE NOT NULL,
                      PRIMARY KEY(id)
);

CREATE TABLE costume (
                         id INT AUTO_INCREMENT,
                         name VARCHAR(255) NOT NULL,
                         description TEXT,
                         imageurl VARCHAR(500) NOT NULL,
                         min_temperature INT,
                         max_temperature INT,
                         make_up ENUM('no', 'light', 'full'),
                         mask ENUM('no', 'yes'),
                         scare_level_id INT NOT NULL,
                         budget_id INT NOT NULL,
                         gender_id INT NOT NULL,
                         age_id INT NOT NULL,
                         group_id INT NOT NULL,
                         time_prep_id INT NOT NULL,
                         PRIMARY KEY(id),
                         FOREIGN KEY (scare_level_id) REFERENCES scare_level(id),
                         FOREIGN KEY (budget_id) REFERENCES budget(id),
                         FOREIGN KEY (gender_id) REFERENCES gender(id),
                         FOREIGN KEY (age_id) REFERENCES age(id),
                         FOREIGN KEY (group_id) REFERENCES group_type(id),
                         FOREIGN KEY (time_prep_id) REFERENCES time_prep(id)
);



CREATE TABLE costume_theme(
                              costume_id INT,
                              theme_id INT,
                              PRIMARY KEY (costume_id, theme_id),
                              FOREIGN KEY (costume_id) REFERENCES costume(id),
                              FOREIGN KEY (theme_id) REFERENCES theme(id)
);



CREATE TABLE costume_color(
                              costume_id INT,
                              color_id INT,
                              PRIMARY KEY (costume_id, color_id),
                              FOREIGN KEY (costume_id) REFERENCES costume(id),
                              FOREIGN KEY (color_id) REFERENCES color(id)
);

INSERT INTO scare_level(name)
VALUES ('spooky'), ('funny'), ('cute'), ('classic'), ('dark'), ('sexy');

INSERT INTO budget(name)
VALUES ('low'), ('medium'), ('high');

INSERT INTO gender(name)
VALUES ('unisex'), ('feminine'), ('masculine'), ('does not matter');

INSERT INTO age(name, years_from, years_to)
VALUES ('child', 0, 12), ('teen', 13, 17), ('adult', 18, 99);

INSERT INTO group_type(name)
VALUES ('solo'), ('couple'), ('group'), ('family-friendly');

INSERT INTO time_prep(name)
VALUES ('last-minute'), ('quick'), ('medium effort'), ('high effort');

INSERT INTO theme(name)
VALUES ('vampires'), ('witches'), ('zombies'), ('ghosts'), ('skeletons'), ('demons'),
       ('monsters'), ('werewolves'), ('mummies'), ('movie, cartoon, game characters'),
       ('villains'), ('superheroes'), ('sci-fi characters'), ('fantasy characters'), ('animals'),
       ('food-themed costumes'), ('memes'), ('retro'), ('clowns'), ('historical'),
       ('professions'), ('DIY');

INSERT INTO color(name)
VALUES ('black'), ('red'), ('white'), ('pink'), ('pastel'), ('green'), ('blue'),
       ('neon');

INSERT INTO costume(name, description, imageurl, min_temperature, max_temperature, make_up, mask, scare_level_id, budget_id,
                    gender_id, age_id, group_id, time_prep_id)
VALUES ('Anime Samurai Girl', 'A sleek black samurai-inspired costume with a wide hat, katana, and dramatic styling,
inspired by anime warrior characters.',
        'anime_samurai_girl.jpeg', 5, 20, 'light', 'no',
        5,2, 2, 3, 1, 3),
       ('Astronaut', 'A sleek NASA-inspired astronaut outfit with metallic color jacket, matching pants and a classic space helmet,
ideal for fans of sci-fi and space aesthetics.', 'astronaut.jpeg', -5, 15,
        'no', 'yes', 4, 2, 1,3, 1, 2),
       ('Beast Boy', 'A casual DIY costume inspired by an animated green-skinned superhero.
     Includes a purple T-shirt, black long-sleeve layer, grey cargo pants, black sneakers,
     a grey belt, and a green wig. Easy to assemble and recognizable from cartoons.',
        'beast_boy.jpeg',10, 25, 'full', 'no',
        1, 1, 3, 1, 1, 2),
       ('Cruella', 'A stylish villainess costume inspired by Disney’s “101 Dalmatians.”
The look includes a form-fitting black dress, a dramatic faux fur coat with black-and-white spots,
vivid red gloves, high-heeled shoes, a half-black-half-white wig, and accessories like a cigarette holder.
Makeup features bold red lips and sharp eye makeup.', 'cruella.jpeg', -5,
        10, 'light', 'no', 4, 3, 2, 3, 1, 3),
       ('Demon', 'A bold and menacing demon costume with a striking combination of red and black. This look includes
a tight red shirt and leather or faux-leather black pants, paired with a black vest. Accessories feature a long,
pointed red tail and platform shoes, and the overall effect is amplified by dramatic red face and hand makeup.',
        'demon.jpeg', 15, 20, 'full', 'no',
        5, 2, 3, 3, 1, 4),
       ('Dracula couple', 'A classic and dramatic vampire couple costume inspired by Dracula legends.
The look for him features a black cape, an ornate red vest or waistcoat, a white ruffled shirt, dress pants, and dark shoes.
The woman’s version includes a long, elegant black lace dress with gothic details, complemented by dark heels and statement jewelry.
Makeup features pale foundation, accentuated cheekbones, smokey eyes, blood-red lips, and optional vampire fangs.',
        'dracula_couple.jpeg', 10, 25, 'full', 'no',
        4, 2, 1, 3, 2, 3),
       ('Forest Fairy', 'A magical forest fairy costume with ethereal details.
The look features delicate wings, a fitted brown top, and a flowing green skirt. Elven ear tips add a fantasy touch,
while heeled shoes adorned with flowers create a whimsical silhouette. The costume is completed with a crystal necklace,
a floral wreath crown, and long, loose romantic waves styled in the hair.',
        'forest_fairy.jpeg', 20, 30, 'light', 'no',
        3, 2, 2, 2, 1, 2),
       ('Ghosts', 'A classic last-minute ghost costume for famely. Usually made from simple white sheets or DIY fabric,
     creating a spooky but playful look. Easy to assemble, lightweight, and perfect for childern, teens or adults.',
        'ghosts.jpeg', 10, 20, 'no', 'yes',
        1, 1, 1, 3, 4, 1),
       ('Hippie couple', 'A colorful retro-inspired 70s hippie couple costume featuring vibrant clothing,
floral elements, peace symbols and relaxed bohemian vibes. Easy to assemble and perfect for themed parties or Halloween.',
        'hippie_couple.jpeg', 15, 25, 'no', 'no',
        3, 2, 1, 3, 2, 2),
       ('Joan of Arc', 'A historical warrior costume inspired by Joan of Arc, featuring medieval armor pieces, a tunic,
boots, and minimalist accessories. A strong and iconic female hero look from the late Middle Ages.',
        'joan_of_arc.jpeg', 20, 30, 'light',
        'no', 4, 2,2,3,1,3),
       ('Jocker', 'A bold and instantly recognizable villain costume inspired by Joaquin Phoenix’s Joker.
The look features a striking maroon-red two-piece suit with shoulder pads, a gold waistcoat, and a green collared shirt.
Bright yellow accents and the character’s signature vivid green hair add a chaotic flair. The outfit is completed by
black dress shoes, white clown face paint with red and blue details, and intense makeup covering the entire face.',
        'jocker.jpeg', 10, 20, 'full', 'no',
        5, 2, 3, 3, 1, 3),
       ('Mad Scientist', 'A wildly eccentric mad scientist costume perfect for parties or themed events.
The look features a white or gray lab coat worn over dark pants, with a black apron, disheveled white wig, and round safety goggles.
Accessories include rubber gloves, a surgical-style mask, and props like an oversized syringe or science equipment.
Makeup is full and dramatic: messy hair, exaggerated dark circles under the eyes, and wild eyebrows can finish the look.',
        'mad_scientist.jpeg', 10, 20, 'full', 'no', 5, 2,
        2, 3, 1, 3),
       ('Matrix character', 'An iconic sci-fi costume inspired by Neo and other heroes from The Matrix. T
    he look features a long black trench coat made from leather or leather-like fabric, a fitted black shirt, and black trousers.
    Accessories include black combat boots and essential narrow-framed black sunglasses. The costume is minimalist but instantly recognizable,
    capturing the mysterious vibe of the film. No makeup or mask is required.', 'matrix_characters.jpeg', 0,
        15, 'no', 'no', 4, 3, 2, 2, 3, 1),
       ('Medieval Knight', 'A classic medieval knight costume that embodies history and heroism. The look typically includes simulated armor elements:
    a metallic silver breastplate, black or dark green tunic, and matching black pants. The ensemble is often finished with a faux chainmail undershirt, leather belt,
     and tall black boots. Optional details: a decorative cape or cloak. Accessories may include a shield and sword for a warrior touch.
        No mask or helmet is necessary, but these can be added for authenticity.', 'medieval_knight.jpeg',
        10, 20, 'no', 'no', 4, 3, 2, 3, 1, 3),
       ('Meme costume', 'A hilarious and instantly recognizable meme costume inspired by the iconic “Mean Girls” movie scene.
                     The look features an oversized hooded blue sweatshirt worn with the hood up, plus large round black sunglasses.
                     The costume is completed by holding a paper or cardboard sign that reads “She doesn’t even go here!”',
        'meme_costume.jpeg', 10, 20, 'no', 'no', 2, 1,
        1, 3, 1, 1),
       ('MIB', 'An iconic and sleek costume inspired by the secret agents from the “Men in Black” movie franchise. The look features a tailored black suit
         with matching black trousers, a crisp white dress shirt, a solid black tie, and polished black dress shoes.
        The outfit is completed by classic black sunglasses, channeling the mysterious and authoritative vibe of an MIB agent.
        Optional props include a fake neuralyzer or a MIB ID badge.', 'mib.jpeg', 5, 20,
        'no', 'no', 4, 2,3, 3, 1,1),
       ('Mim', 'A classic mime costume inspired by Parisian street performers. The look features a black and white striped shirt,
        black pants, and black suspenders. The outfit is finished with a black beret and white gloves. Accessories may include a red scarf or
        bandana tied around the neck. Full white face paint with exaggerated black eyebrows and details give the characteristic silent movie look,
        often completed with black lips.', 'mim.jpeg', 10, 25, 'full', 'no',
        4, 1, 3, 2, 1, 1),
       ('Mummy', 'A spooky and classic mummy costume, perfect for quick DIY or upgraded Halloween looks.
              The costume features white bandages or strips of muslin, cheesecloth, or bedsheet fabric wrapped haphazardly around the torso, legs,
              and arms to mimic the look of an undead Egyptian mummy. The base can be worn over tight white clothing for comfort.
              Light makeup completes the effect—foundation in pale or gray tones, plus subtle brown shading and dark circles to give a dusty, ancient look.',
        'mummy.jpeg', 10, 25, 'light', 'yes', 1, 1, 1,
        1, 3, 3),
       ('Pumpking', 'Comfortable to wear over clothes, this outfit may include ribbed cuffs and a zip or Velcro closure for easy fitting.
                 Makeup is optional; a mask provides all the character’s charm.', 'pumpking.jpeg',
        10, 20, 'no', 'yes', 4, 2, 1,1,1,2),
       ('Skeletons', '  The look typically features matching black jumpsuits or fitted clothing with white bone prints on the front and back,
creating a realistic skeleton effect. Family may add accessories, such as black gloves with skeleton bones, black or white shoes, and optional capes or hats.
Makeup can be light (black and white contouring for skull effects) or full-face skeleton paint with eye sockets, cheek hollows, and jaw details.',
        'skeletons.jpeg', 10, 20, 'full', 'no', 1, 2, 1,
        3, 4, 4),
       ('Spider-man', 'A casual, streetwear-inspired Spider-Man costume that puts a modern spin on the classic superhero look.
               The outfit consists of a red Spider-Man themed hoodie with printed web and spider logos, paired with blue or black jeans for comfort and everyday style.
               The look is completed by a full-face mask or half-mask for a playful but instantly recognizable superhero vibe. No makeup is required,
               as the mask provides the iconic Spider-Man effect', 'spiderman.jpeg', 0, 15,
        'no', 'yes', 4, 1, 2, 2, 1,1),
       ('Vampire Goth', 'A seductive and dramatic vampire goth costume inspired by gothic and vampiric elegance.
                     The look features a fitted black or deep red velvet or lace dress, often with a plunging neckline, corset or bodice,
                     and a high slit or short skirt. The costume is typically accessorized with a cape, fishnet stockings, lace gloves,
                     and ornate gothic jewelry such as chokers and bat motif chains. Pale foundation and bold makeup finish the vampiric look:
                     smokey eyes, sharp eyeliner, deep red or black lips, and highlighted cheekbones. Optional details include black or burgundy nails
                    and dramatic updo or long hair with colored streaks.', 'vampire_goth.jpeg', 20, 25,
        'full', 'no', 6, 3, 2, 3,1,3),
       ('Werewolf', 'A frightening and dramatic werewolf couple costume ideal for Halloween, parties, or monster-themed events.
                 Each costume includes a plush faux fur tunic or vest in shades of grey or brown, with matching paw-style gloves and a furry tail.
                  Torn plaid shirts or pants add a wild, transformed look. The centerpiece is a realistic, molded latex werewolf mask featuring wolf ears, fur accents,
                 and a snarling jaw—no makeup required thanks to the mask’s detail. Optional accessories include clawed feet covers or boots and scratch-marked fabric.
                 Designed to be worn as a duo for maximum effect', 'werewolfs.jpeg', 0, 15,
        'no', 'yes', 1, 3, 1,3,3,3),
       ('Witch', 'A charming and playful witch costume that blends traditional style with a modern, fashionable twist.
              The look features a short or midi black dress with fun details like ruffles, glitter, or pastel accents.
              Accessories may include a pointy witch hat, striped tights, a magic wand, and sparkly jewelry. Optional touches: a tulle skirt overlay,
              colorful hair streaks, or a pumpkin or star-print cape. Makeup is light, shimmery, and bright, focusing on glitter eyeshadow, glowy cheeks, and a glossy lip.',
        'witch.jpeg', 15, 25, 'light', 'no',3, 2, 2,
        2, 1,2),
       ('Zombie survivor', 'A rugged and dramatic zombie apocalypse survivor costume, combining torn and distressed casual clothing such as cargo pants,
                        jeans, layered shirts, and a weathered jacket. The outfit features improvised bandages, fake bloodstains, dirty patches,
                        and optional props—like toy weapons (axe, bat, crowbar), utility belt, backpack, or flashlight.
                        Hard, practical accessories (gloves, boots) add authenticity.', 'zombie_survivor.jpeg',
        -10, 20, 'no', 'no', 5, 1, 1,3,1,2),
       ('Zootopia characters', 'A playful and adorable couple costume inspired by Judy Hopps (rabbit) and Nick Wilde (fox) from Zootopia.
                            Judy Hopps costume features a fitted blue crop top and leggings or navy-blue police uniform-inspired outfit with knee pads,
                            a utility belt, and a police badge. Essential accessories include tall bunny ears, grey wig or faux fur accents,
                            and bunny paw slippers or shoes. Nick Wilde’s costume is a casual but sharp ensemble with a bright green palm-print button-down shirt,
                            blue striped tie, khaki pants, and fox ears and tail accessories.', 'zootopia_characters.jpeg',
        10, 20, 'no', 'no', 3, 2, 1, 2,2,3);

INSERT INTO costume_theme(costume_id, theme_id)
VALUES (1, 10), (1, 14), (1, 22), (1, 20),
       (2, 13), (2, 21),
       (3, 10), (3, 12), (3, 14), (3, 22),
       (4, 10), (4, 11), (4, 14), (4, 22),
       (5, 6), (5, 7), (5, 14), (5, 22),
       (6, 1), (6, 11), (6, 14), (6, 10),
       (6, 20), (6, 22),
       (7, 14), (7, 10),
       (8, 4), (8, 22),
       (9, 18), (9, 10), (9, 17),
       (10, 20), (10, 10),
       (11, 10), (11, 11), (11, 14),
       (12, 10), (12, 21), (12, 11),
       (13, 22), (13, 14), (13, 10),
       (14, 21), (14, 10), (14, 20),
       (15, 22), (15, 17), (15, 10),
       (16, 22), (16, 14), (16, 17), (16, 10),
       (17, 19), (17, 22),
       (18, 22), (18, 7), (18, 9),
       (19, 16),
       (20, 7), (20, 5),
       (21, 12), (21, 18), (21, 10), (21, 14),
       (22, 14), (22, 7), (22, 10), (22, 1),
       (23, 8), (23, 10), (23, 7),
       (24, 22), (24, 14), (24, 10), (24, 20),
       (24, 2),
       (25, 3), (25, 10), (25, 22),
       (26, 15), (26, 14), (26, 10);

INSERT INTO costume_color(costume_id, color_id)
VALUES (1, 1),
       (2, 3),
       (3, 1), (3, 6), (3, 8),
       (4, 1), (4, 2), (4, 3),
       (5, 1), (5, 2),
       (6, 1), (6, 2), (6, 3),
       (7, 3), (7, 6), (7, 5),
       (8, 3),
       (9, 1), (9, 3), (9, 8), (9, 4),
       (10, 3), (10, 1),
       (11, 2), (11, 6),
       (12, 1), (12, 2), (12, 3),
       (13, 1),
       (14, 1),
       (15, 7),
       (16, 1), (16, 3),
       (17, 1), (17, 3),
       (18, 3),
    (19, 1), (19, 6),
    (20, 1), (20,3),
    (21, 2), (21, 7),
    (22, 1), (22, 2),
    (23, 1), (23, 2), (23, 3), (23, 7),
    (24, 1),
    (25, 1),
    (26, 6), (26, 7), (26, 4);

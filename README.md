# Mini-Rapport de développement Mobile

## Fonctionnalités développées:

- Affichage des catégories de repas dans une grille
- Affichage des détails des repas d'une catégorie sélectionnée dans une grille
- Utilisation de l'API de TheMealDB pour récupérer les informations sur les catégories de repas et les détails des repas
- Utilisation de Picasso pour afficher les images des repas
- Utilisation de la bibliothèque OkHttp pour effectuer les requêtes réseau


## Implémentation

### Général

Notre application est constituée de trois activités :
- La page d'accueil qui contient la liste des catégories de plat
- La page de détail de catégorie, qui contient la liste des plats de la catégorie choisie
- la page de recette, qui contient les détails de la recette du plat choisi

Nous avons implémenté une toolbar, intégré dans les layoutc des activités qui contiennent le titre de l'activité en cours (My Food pour la page d'accueil, le nom de la catégorie choisis pour la page de détail de catégorie, et le nom du plat pour la page de recette), ainqi que un bouton retour, pour les pages autres qua la page d'accueil.


Nous avons utilisé OKHTTP pour avoir accès à l'API TheMealDB, puis utilisé la bibliothèque GSON pour déserialiser les données reçues. Les images sont chargées grâce à la bibliothèque Picasso.


### Page d'accueil et page de détail

Ces pages sont constituées d'un recyclerview, dont les éléments sont des Cardview, ce qui permet d'afficher des images et du texte de manière plus esthétiques.
Les Cardview sont disposées en deux colonnes grace à un LayoutManager. Pour la page d'accueil (respectivement la page de détail), les cardviews contient le nom d'une catégorie (respectivement le nom d'un plat) et l'image représentant la catégorie (respectivement le plat).


### Page de recette

La page de recette contient :
- L'image du plat
- Un lien cliquable menant vers la vidéo YouTube correspondant à la vidéo de la recette. Toutefois si l'application YouTube est déjà ouverte, on bascule bien vers l'application, mais la vidéo n'est pas cherchée. Nous n'avons pas résolu ce problème, car pour cela, il faudrait sûrement utiliser directement l'API YouTube.
- La liste des ingrédients est intégrée dans une ListView, car le nombre d'ingrédients utilisé est indéterminé. La taille de l'affichage de la ListeView est adaptée pour correspondre exactement à la somme de la taille des items, afin de pouvoir voir tous les ingrédients sans scroller, et ainsi pouvoir bloquer la possibilité de scroller.
- Il y a ensuite les instructions dans une Textview.

Ces éléments sont inclus dans un RelativeLayout, afin de pouvoir bien les disposer les uns par rapport aux autres. Ce RelativeLayout est lui-même inclus dans une ScrollView, pour pouvoir faire défiler tous les éléments en bloc. Pour la page recette, la toolbar est inclus dans le RelativLayout, et donc dans le Scrollview, et donc est cachée si l'on scroll vers le bas. Cela permet de maximiser la place pour les informations utiles (ingrédients et instructions).


##Difficultés rencontrées

Nous avons eu des difficultés pour mettre en place la navigation entre les vues de l'application et pour afficher les données dans une vue de type grille, mais nous avons réussi à les surmonter grâce à la recherche et à l'aide du cours de notre enseignant.
L'implémentation de la Liste d'ingrédient a aussi été problématique, et en particulier le fait de rendre la listView inscrollable.



# ImageCitation

Une application Android en Java qui transforme tes citations en images stylées. J'ai implémenté les fondamentaux de la POO avec des classes Template modulaires, le parsing JSON avec Gson pour charger les styles visuels, et les méthodes Android onCreate() pour initialiser le rendu Canvas des images

![gif](https://media1.giphy.com/media/v1.Y2lkPTc5MGI3NjExdXNheTh2OHc1MTczeGw5b3dqb2Ztcjg4bDAxMHB3bzQ1c2todDgyeCZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/c2hwP7u1G1HSFANuHa/giphy.gif)

## Fonctionnalités

- **Saisie simple** : Entrez une citation et le nom de l'auteur
- **Templates visuels** : Choix parmi plusieurs thèmes prédéfinis
- **Génération automatique** : Création d'images haute qualité avec mise en forme professionnelle
- **Partage facile** : Sauvegarde et partage des images générées
- **Interface intuitive** : Design moderne et ergonomique

**Templates** :
![alt text](https://github.com/Maaloul-Khalil/imageCitation/blob/master/themesPreview.png)

### Structure du projet
```
app/
├── src/main/java/com/example/imageCitation/
│   ├── MainActivity.java          # Activité principale
│   ├── Activity2.java            # Activité secondaire
│   ├── ActivityPlus.java         # Activité supplémentaire
│   ├── QuoteFetcher.java         # Récupération de citations
│   ├── graphics/
│   │   └── QuoteRenderer.java    # Rendu des citations
│   ├── managers/
│   │   └── TemplateManager.java  # Gestion des templates
│   ├── models/
│   │   └── Template.java         # Modèle de template
│   └── utils/
│       ├── BtnImg.java           # Utilitaires boutons
│       └── TemplateUtils.java    # Utilitaires templates
├── assets/
│   ├── motiv1.json              # Template motivationnel 1
│   ├── motiv2.json              # Template motivationnel 2
│   ├── simple1.json             # Template simple 1
│   ├── simple2.json             # Template simple 2
│   └── plus.html/css            # Contenu web
└── res/
    ├── drawable/                # Images et backgrounds
    └── layout/                  # Interfaces utilisateur
```

## Technologies utilisées

- **Java** : Langage principal de développement
- **Android SDK** : Framework de développement mobile
- **Canvas API** : Rendu graphique des images
- **Gson** : Sérialisation/désérialisation JSON
- **JSON** : Configuration des templates visuels

## Système de Templates

L'application utilise un système de templates JSON pour définir l'apparence des citations :

```json
{
  "themeName": "Lunerose",
  "background": "simple1",
  "text": {
    "quote": {
      "position": { "x": "50%", "y": "42%" },
      "style": {
        "color": "#FFFFFF",
        "size": "26pt",
        "quotation_marks": {
          "enabled": true,
          "style": "double_curved"
        },
        "font": {
          "family": "Bricolage Grotesque",
          "weight": "bold"
        }
      }
    },
    "author": {
      "position": { "x": "50%", "y": "85%" },
      "style": {
        "color": "#FFFFFF",
        "size": "18pt",
        "prefix": "- "
      }
    }
  }
}
```

***GIF recommandé ici** : Démonstration du changement de templates*

## Processus de génération

1. **Saisie utilisateur** : Citation + auteur
2. **Chargement template** : Lecture du fichier JSON avec Gson
3. **Préparation Canvas** : Création du canevas de dessin
4. **Application styles** : Configuration des Paint selon le template
5. **Rendu final** : Dessin du texte sur l'arrière-plan
6. **Affichage** : Présentation de l'image générée

## 📧 Contact
**Email** : [maaloulmohamedkhalil@isimsf.u-sfax.tn](mailto:maaloulmohamedkhalil@isimsf.u-sfax.tn)

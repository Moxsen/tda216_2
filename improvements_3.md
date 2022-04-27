# Förbättringar Lab 3
## Kontroll av förbättringsförslag från lab 2
Vi åtgärdar följande förslag ifrån föregående labb.
- Sätter en minimum och maximum bredd på filterfönstret genom att sätta en minimibredd på elementen inuti respektive sida av SplitPane:n

## Konkreta förbättringar
- En knapp som nollställer sökfiltret till standardvärden.
- Istället för att ha förklarande text vid varje sökfilter, använd andra default-värden för att visa vad filtret innebär, t.ex. "Värdsdelar" eller "Ursprung" för att visa att ComboBoxen med land sorterar baserat på "Ursprung".
- Ta bort resultat som har under en viss gräns i matchningsvärde.
- Lägg till en favicon till programmet.
- Möjlighet att markera recept osm favoriter.

## Bristfällig pliancy
- Programmet har fortfarande lite bristfällig feedback när aktiverade filter inte har någon ytterligare inverkan på sökresultatet, t.ex. när maxtiden inte längre har någon inverkan på filtreringsresultatet. Man skulle kunna blinka till med en laddningssymbol varje gång filtret ändras, även om det egentligen bara returnerar samma filtrering
- Programmet saknar forfarande nyttig feedback såsom totala antalet visade recept, totala antalet filtrerade recept eller att man har nått slutet på alla recept.
 
# Förbättringar Lab 2
## Kontrollerna
Kontrollerna känns säkra, och tillåter enbart fördefinierade, giltiga, inputs. Det hade kunnat finnas en poäng i att tillåta att användaren bokstaverar input till ComboBox, för att slippa bläddra hela vägen ner till den input man vill åt.

Ingen kontroll har fokus när programmet startar.
Det skulle kunna finnas en poäng med att ge svårighetsgraden, eller det översta filtret fokus när programmet startar.
Ingen kontroll får fokus när man kommer tillbaka från en detaljerad vy. Det skulle kunna finnas en poäng med att låta senaste kontrollen som hade fokus, få fokus igen.

Konkreta förbättringar inkluderar:
- Sätta en minimum och maximum bredd på filterfönstret.
- Gör så att knappen "Close" inte blir för stor när man har maximerat fönstret.
- Istället för att ha förklarande text vid varje sökfilter, använd andra default-värden för att visa vad filtret innebär, t.ex. "Värdsdelar" eller "Ursprung" för att visa att ComboBoxen med land sorterar baserat på "Ursprung".
- Ta bort resultat som har under en viss gräns i matchningsvärde.
- Lägg till en favicon till programmet

Bristfällig pliancy
- Programmet har lite bristfällig feedback när aktiverade filter inte har någon ytterligare inverkan på sökresultatet, t.ex. när maxtiden inte längre har någon inverkan på filtreringsresultatet. Man skulle kunna blinka till med en laddningssymbol varje gång filtret ändras, även om det egentligen bara returnerar samma filtrering
- Programmet saknar nyttig feedback såsom totala antalet visade recept, totala antalet filtrerade recept eller att man har nått slutet på alla recept.
 
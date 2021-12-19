*Project: "2020 Election Results Compared with Demographics and Economic Factors"
*Authors: Anya Zarembski, Theresa Sheridan, and Alex Baker
*Date: 5/7/2021
*Sources: SAS Documentation Help Center - multiple pages (for graph customization), dqydj.com - "Average, Median, Top 1%, and all United States Household Income Percentiles in 2020" (for Income Brackets), SAS Community -"If then else statement multiple variables/conditions" (for help with Region Variable Names length)

*importing data set;
proc import datafile = "/folders/myfolders/county_statistics.xlsx" out = county dbms = xlsx;
	sheet = "Data";
run;

data data_set;
	*Cleanse the data of any observations with missing values;
	set county;
	if missing(TotalPop)|missing(Men)|missing(Women)|missing(Hispanic)|missing(White)|missing(Black)|missing(Native)|missing(Asian)|missing(Pacific)|missing(VotingAgeCitizen)|missing(Income)|missing(Poverty)|missing(Construction)|missing(Production)|missing(Employed)|missing(Unemployment) then delete;
	
	*Combining Asian and Pacific Islander;
	AAPI = Asian + Pacific;
	DROP Asian Pacific;
	
	*Combining Construction and Production;
	blue_collar = Production + Construction;
	DROP Construction Production;
	
	*Create new variable regarding labor types;
	if blue_collar > 42 then labor_type = "Heavily Manual Labor";
	else if blue_collar > 30 and blue_collar <= 50 then labor_type = "Mix of Job Type";
	else if blue_collar <= 30 then labor_type = "White-Collar Jobs";
	
	*Create new variable called Winner to denote the candidate who took the majority votes of that county;
	if percentage20_Donald_Trump > .5 then Winner = "Trump";
	else if percentage20_Joe_Biden > .5 then Winner = "Biden";
	else Winner = "Other";
	
	*Determine Physical Number of Votes for Each Candidate;
	Votes_for_Trump = percentage20_Donald_Trump*total_Votes20;
	Votes_for_Biden = percentage20_Joe_Biden*total_Votes20;
	
	*Assigning Regions;
	length Region $ 10; *Allows Region names to be longer than 5 letters (because South's length would otherwise constrict them);
	if state in ("DC","OK","TX","AR","LA","KY","TN","MS","AL","WV","VA","NC","SC","GA","FL","AK","MD","DE") then Region = "South";
	else if state in ("NJ","NY","CT","RI","MA","VT","NH","ME","PA") then Region = "Northeast";
	else if state in ("CA","NV","UT","AZ","CO","NM","WA","OR","ID","MT","WY","HI","AL") then Region = "West";
	else Region = "Midwest";
	
	*Assigning Majority Race in Each County to a Variable;
	if White > 50 then majority_race = "White";
		else if Black > 50 then majority_race = "Black";
		else if Hispanic > 50 then majority_race = "Hispanic";
		else if (AAPI) > 50 then majority_race = "AAPI";
		else if Native > 50 then majority_race = "Native American";
		else majority_race = "None";
		
	*Assigning Majority Sex in Each County to a Variable;
	if (Men / TotalPop) > 0.5 then majority_sex = "Men";
		else if (Women / TotalPop) > 0.5 then majority_sex = "Women";
		
	*Assigning Each County to an Income Bracket based on Average Income of all Counties;
	if (Income < 23502) then income_bracket = "Very Low";
	else if Income >= 23502 and Income < 44488 then income_bracket = "Low";
	else if Income >= 44488 and Income < 100000 then income_bracket = "Middle";
	else if Income >= 100000 and Income < 160522 then income_bracket = "High";
	else if Income >= 160522 then income_bracket = "Very High";
	
	*Assigning County Voter Turnout into Distinct Groups;
	Percent_Voter_Turnout = total_votes20 / VotingAgeCitizen;
	if Percent_Voter_Turnout > 0.7 then voter_turnout = "High";
	else if Percent_Voter_Turnout > 0.4 and Percent_Voter_Turnout <= 0.7 then voter_turnout = "Medium";
	else if Percent_Voter_Turnout <= 0.4 then voter_turnout = "Low";
	
	*Create a more General Racial Variable and Assign Counties to the Correct Designation;
	if White >= 0 and White < 10 then percent_white = "Very High Minority Population";
	else if White < 50 and White >= 10 then percent_white = "Majority-Minority";
	else if White >= 50 and White <= 95 then percent_white = "White Majority";
	else if White > 95 then percent_white = "Very White";
run;

*Creates a subset for each region;
data Midwest;
	set data_set;
	if Region = "Midwest";
run; 

data South;
	set data_set;
	if Region = "South";
run;

data Northeast;
	set data_set;
	if Region = "Northeast";
run;

data West;
	set data_set;
	if Region = "West";
run;

*View Summary Statistics Regarding Votes for Each Candidate for Each Region;
title "Midwest";
proc means data = midwest;
	var Votes_for_Trump Votes_for_Biden;
run;

title "South";
proc means data = south;
	var Votes_for_Trump Votes_for_Biden;
run;

title "Northeast";
proc means data = northeast;
	var Votes_for_Trump Votes_for_Biden;
run;

title "West";
proc means data = west;
	var Votes_for_Trump Votes_for_Biden;
run;
	
*Create a Table to Display the Number of Counties that Each Race is a Majority In;
title "Majority Race by County";
proc freq data = data_set;
	tables majority_race;
run;

*Create a Table to Display the Number of Counties that Each Labor Type has a Majority in;
title "Majority Labor Type by County";
proc freq data = data_set;
	tables labor_type;
run;

*Create a Table to Display Voter Turnout for Each County in One of Three Categories;
title "Voter Turnout Category by County";
proc freq data = data_set;
	tables voter_turnout;
run;

*Create a Table to Display the Winner for Each County;
title "Winner by County";
proc freq data = data_set;
	tables Winner;
run;

*Create a Table to Display Majority Sex for Each County;
title "Majority Sex Category by County";
proc freq data = data_set;
	tables majority_sex;
run;

*Create a Table to Display the Frequency of Each Income Brackets;
title "Income Bracket Frequencies";
proc freq data = data_set;
	tables income_bracket;
run;

*Create a Table to Display the Frequency of Each Category of "Percent White";
title "Percent-White Category Frequencies";
proc freq data = data_set;
	tables percent_white;
run;

*These two graphs look at the Relative Frequency of Votes for Biden vs the Relative Frequency of Votes for Trump;
proc sgplot data = data_set;
	histogram percentage20_Joe_Biden;
	title "All Counties Biden Vote";
run;

proc sgplot data = data_set;
	histogram percentage20_Donald_Trump;
	title "All Counties Trump Vote";
run;

*The following are all summarizing graphs. The next two look at Very High Majority-Minority counties' voting trends vs the voting trends of Very White Counties on a spectrum;

proc sgplot data = data_set;
	vbar percent_white / response=percentage20_Joe_Biden stat=mean;
	title "Racial Diversity vs Biden Votes";
run;

proc sgplot data = data_set;
	vbar percent_white / response=percentage20_Donald_Trump stat=mean;
	title "Racial Diversity vs Trump Votes";
run;

*These next two graphs look at Votes from Different Races in More Detail;
title "Mean Votes by Majority Race for Biden";
proc sgplot data=WORK.DATA_SET;
	vbar majority_race / response=percentage20_Joe_Biden stat=mean;
	yaxis grid;
run;

title "Mean Votes by Majority Race for Trump";
proc sgplot data=WORK.DATA_SET;
	vbar majority_race / response=percentage20_Donald_Trump stat=mean;
	yaxis grid;
run;

*Take a non-replacement random sample to make the following scatterplots less crowded;
title "Random Sample of 100 Counties without Replacement";
proc surveyselect data = data_set method = srs rep = 1 sampsize = 100
	out = random_samp;
	id _all_;
run;

*These scatterplots compare county size to the percentage of votes for each candidate;
proc sgplot data=random_samp;
	reg x = TotalPop y = percentage20_Donald_Trump / filledoutlinedmarkers
	markerfillattrs=(color=pink)
	markeroutlineattrs=(color=red thickness=2)
   	markerattrs=(symbol=circlefilled size=10);
	title "Votes for Trump by County Size";
run;

proc sgplot data=random_samp;
	reg x = TotalPop y = percentage20_Joe_Biden;
	title "Votes for Biden by County Size";
run;

*These next two graphs compare the percentage of blue-collar workers in each county to the percent of votes for each candidate;
proc sgplot data=random_samp;
	reg x = blue_collar y = percentage20_Donald_Trump;
	title "Blue-Collar Votes for Trump";
run;

proc sgplot data=random_samp;
	reg x = blue_collar y = percentage20_Joe_Biden;
	title "Blue-Collar Votes for Biden";
run;

*These next two plots compare the percent of potential voters who actually cast a ballot vs the percent of votes for each candidate;
proc sgplot data=random_samp;
	reg x = Percent_Voter_Turnout y = percentage20_Joe_Biden;
	title "Votes for Biden vs Voter Turnout";
run;

proc sgplot data=random_samp;
	reg x = Percent_Voter_Turnout y = percentage20_Donald_Trump;
	title "Votes for Trump vs Voter Turnout";
run;

*Return back to full data set now. Look at voting patterns for each candidate in regard to Sex and Income Level;
title "Mean Votes by Majority Sex for Biden";
proc sgplot data=WORK.DATA_SET;
	vbar majority_sex / response=percentage20_Joe_Biden stat=mean;
	yaxis grid;
run;

title "Mean Votes by Majority Sex for Trump";
proc sgplot data=WORK.DATA_SET;
	vbar majority_sex / response=percentage20_Donald_Trump stat=mean;
	yaxis grid;
run;

title "Votes for Biden based on Income Level";
proc sgplot data=WORK.DATA_SET;
	vbar income_bracket / response=percentage20_Joe_Biden stat=mean;
run;

title "Votes for Trump based on Income Level";
proc sgplot data=WORK.DATA_SET;
	vbar income_bracket / response=percentage20_Donald_Trump stat=mean;
run;

*Analyzing Rust Belt by seeing how different labor forces voted for Trump and Biden;
data rust_belt;
	set data_set;
	if state = "PA" | state = "MI" | state = "WI";
run;

proc sgplot data = WORK.rust_belt;
	vbar labor_type / response =percentage20_Donald_Trump stat=mean;
	title "Votes for Trump Based on Labor in the Rust Belt (PA, WI, MI)";
run;

proc sgplot data = WORK.rust_belt;
	vbar labor_type / response =percentage20_Joe_Biden stat=mean;
	title "Votes for Biden Based on Labor in the Rust Belt (PA, WI, MI)";
run;

*Analyzing Deep South States vs New England States;
data deep_south;
	set data_set;
	if state = "TX" | state = "LA" | state = "MS" | state = "AL" | state = "GA" | state = "SC" | state = "FL";
run;

data new_england;
	set data_set;
	if state = "CT" | state = "ME" | state = "MA" | state = "NH" | state = "RI" | state = "VT";
run;

*Use the data sets above to compare Race and Income Data to Voting Trends in both the Deep South and New England;
proc sgplot data = deep_south;
	vbar percent_white / response=percentage20_Joe_Biden stat=mean;
	title "Racial Diversity vs Biden Votes in the Deep South";
run;

proc sgplot data = deep_south;
	vbar percent_white / response=percentage20_Donald_Trump stat=mean;
	title "Racial Diversity vs Trump Votes in the Deep South";
run;

proc sgplot data = new_england;
	vbar percent_white / response=percentage20_Joe_Biden stat=mean;
	title "Racial Diversity vs Biden Votes in New England";
run;

proc sgplot data = new_england;
	vbar percent_white / response=percentage20_Donald_Trump stat=mean;
	title "Racial Diversity vs Trump Votes in New England";
run;

proc sgplot data = deep_south;
	vbar income_bracket / response=percentage20_Joe_Biden stat=mean;
	title "Income Bracket vs Biden Votes in the Deep South";
run;

proc sgplot data = deep_south;
	vbar income_bracket / response=percentage20_Donald_Trump stat=mean;
	title "Income Bracket vs Trump Votes in the Deep South";
run;

proc sgplot data = new_england;
	vbar income_bracket / response=percentage20_Joe_Biden stat=mean;
	title "Income Bracket vs Biden Votes in New England";
run;

proc sgplot data = new_england;
	vbar income_bracket / response=percentage20_Donald_Trump stat=mean;
	title "Income Bracket vs Trump Votes in New England";
run;
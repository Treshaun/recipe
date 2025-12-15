# SOAP Web Service Testing Guide


## Prerequisites

### Software Required
- **Java 8+** installed
- **SoapUI** (Download from: https://www.soapui.org/downloads/soapui/)
- **Maven** (optional, for building)
- **IntelliJ IDEA** (recommended)

### Server Must Be Running
Before testing, ensure the SOAP server is started:
```
URL: http://localhost:8080/ws/recipes
WSDL: http://localhost:8080/ws/recipes?wsdl
```

---

## Starting the Server

### Option 1: Using IntelliJ IDEA
1. Open the project in IntelliJ
2. Run the **ServerPublisher** configuration
3. Wait for console message: `Server started at: http://localhost:8080/ws/recipes`

### Option 2: Command Line
```bash
cd C:\Users\Rani\IdeaProjects\recipe
mvn clean compile
java -cp target/classes com.fsb.soap.ServerPublisher
```

### Verify Server is Running
Open browser and navigate to:
```
http://localhost:8080/ws/recipes?wsdl
```
You should see the WSDL XML document.

---

## Testing with SoapUI

### Step 1: Create New SOAP Project

1. **Launch SoapUI**
2. **File** → **New SOAP Project**
3. Fill in the details:
   - **Project Name**: `RecipeServiceTest`
   - **Initial WSDL**: `http://localhost:8080/ws/recipes?wsdl`
   - Check **Create Requests**
   - Click **OK**

4. SoapUI will automatically:
   - Parse the WSDL
   - Generate test requests for all operations
   - Create a project structure

### Step 2: Explore Generated Structure

You should see:
```
RecipeServiceTest
└── RecipeServiceImplService
    └── RecipeServiceImplPort
        ├── addRecipe
        ├── getAllRecipes
        └── getRecipe
```

---

## SOAP Request Examples

### Test 1: Get All Recipes

**Operation**: `getAllRecipes`

**Request**:
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:soap="http://soap.fsb.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <soap:getAllRecipes/>
   </soapenv:Body>
</soapenv:Envelope>
```

**Expected Response**:
```xml
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
   <S:Body>
      <ns2:getAllRecipesResponse xmlns:ns2="http://soap.fsb.com/">
         <return>
            <id>1</id>
            <name>Couscous</name>
            <ingredients>Semolina, Lamb, Veggies</ingredients>
            <price>15 TND</price>
         </return>
         <return>
            <id>2</id>
            <name>Lablabi</name>
            <ingredients>Chickpeas, Bread, Egg</ingredients>
            <price>4 TND</price>
         </return>
      </ns2:getAllRecipesResponse>
   </S:Body>
</S:Envelope>
```

---

### Test 2: Add New Recipe

**Operation**: `addRecipe`

**Request Example 1 - Brik**:
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:soap="http://soap.fsb.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <soap:addRecipe>
         <arg0>
            <id>3</id>
            <name>Brik</name>
            <ingredients>Egg, Tuna, Pastry</ingredients>
            <price>3 TND</price>
         </arg0>
      </soap:addRecipe>
   </soapenv:Body>
</soapenv:Envelope>
```

**Request Example 2 - Ojja**:
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:soap="http://soap.fsb.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <soap:addRecipe>
         <arg0>
            <id>4</id>
            <name>Ojja</name>
            <ingredients>Tomatoes, Eggs, Merguez, Peppers</ingredients>
            <price>8 TND</price>
         </arg0>
      </soap:addRecipe>
   </soapenv:Body>
</soapenv:Envelope>
```

**Request Example 3 - Makroudh**:
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:soap="http://soap.fsb.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <soap:addRecipe>
         <arg0>
            <id>5</id>
            <name>Makroudh</name>
            <ingredients>Semolina, Dates, Honey</ingredients>
            <price>2 TND</price>
         </arg0>
      </soap:addRecipe>
   </soapenv:Body>
</soapenv:Envelope>
```

**Expected Response**:
```xml
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
   <S:Body>
      <ns2:addRecipeResponse xmlns:ns2="http://soap.fsb.com/"/>
   </S:Body>
</S:Envelope>
```

**Server Console Output**:
```
Server: Added recipe Brik
```

---

### Test 3: Get Single Recipe by ID

**Operation**: `getRecipe`

**Request - Get Couscous (ID=1)**:
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:soap="http://soap.fsb.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <soap:getRecipe>
         <arg0>1</arg0>
      </soap:getRecipe>
   </soapenv:Body>
</soapenv:Envelope>
```

**Expected Response**:
```xml
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
   <S:Body>
      <ns2:getRecipeResponse xmlns:ns2="http://soap.fsb.com/">
         <return>
            <id>1</id>
            <name>Couscous</name>
            <ingredients>Semolina, Lamb, Veggies</ingredients>
            <price>15 TND</price>
         </return>
      </ns2:getRecipeResponse>
   </S:Body>
</S:Envelope>
```

**Request - Get Lablabi (ID=2)**:
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:soap="http://soap.fsb.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <soap:getRecipe>
         <arg0>2</arg0>
      </soap:getRecipe>
   </soapenv:Body>
</soapenv:Envelope>
```

**Request - Get Non-Existent Recipe (ID=999)**:
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:soap="http://soap.fsb.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <soap:getRecipe>
         <arg0>999</arg0>
      </soap:getRecipe>
   </soapenv:Body>
</soapenv:Envelope>
```

**Expected Response** (Recipe not found):
```xml
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
   <S:Body>
      <ns2:getRecipeResponse xmlns:ns2="http://soap.fsb.com/"/>
   </S:Body>
</S:Envelope>
```







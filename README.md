## Setup & Run

### 1. Clone the project

```bash
git clone https://github.com/<your-username>/<repository-name>.git
cd <repository-name>
```

### 2. Create `.env`

Create a `.env` file in the project root:

```env
GOOGLE_API_KEY=your_google_api_key
```

> **Important:** Never commit your `.env` file or API key to GitHub.

Add `.env` to `.gitignore`:

```gitignore
.env
```

### 3. Configure the environment variable

Make sure `GOOGLE_API_KEY` is available to the application as an environment variable.

**Windows PowerShell:**

```powershell
$env:GOOGLE_API_KEY="your_google_api_key"
```

**Linux / macOS:**

```bash
export GOOGLE_API_KEY="your_google_api_key"
```

### 4. Run the application

Start the Java application from your IDE or using Gradle/Maven.

The agent can then process requests such as:

```text
You > What is the time in Pune?
```

The agent uses the `getCurrentTime` function tool to retrieve the current time using Java's `ZoneId` and `ZonedDateTime` APIs.

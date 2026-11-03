// Public pages required by Google Play: a privacy policy URL and a web page explaining account deletion.
const style = `<style>body{font-family:Arial,sans-serif;max-width:720px;margin:32px auto;padding:0 16px;line-height:1.55;color:#222}h1{color:#9E0000}</style>`;
const CONTACT = process.env.CONTACT_EMAIL || "your-email@example.com";

const privacy = `<!doctype html><html><head><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1"><title>FitLog Privacy Policy</title>${style}</head><body>
<h1>FitLog Privacy Policy</h1>
<p>Last updated: ${new Date().toISOString().slice(0, 10)}</p>
<p>FitLog is a workout-logging app. This policy explains what we collect and why.</p>
<h2>Data we collect</h2>
<ul>
<li><b>Account data:</b> your name and email address. If you sign in with Google we receive your name and verified email from Google; we never receive your Google password.</li>
<li><b>Password:</b> for email accounts, your password is hashed on your device before it is sent, and hashed again (bcrypt) on our server. We never store or see the plain-text password.</li>
<li><b>Workout data:</b> workout names, dates, durations, exercises, sets, reps and weights that you log.</li>
<li><b>Device push token:</b> an identifier from Firebase Cloud Messaging so we can send you notifications you have switched on.</li>
<li><b>Settings:</b> language, units and notification preferences.</li>
</ul>
<h2>How we use it</h2>
<p>To run the app, sync your workouts between your phone and your account, and send the notifications you choose. We do not sell your data, show ads, or use it for tracking.</p>
<h2>Where it is stored</h2>
<p>On your phone (local database) and on our server database (MongoDB Atlas), transmitted over HTTPS. Push delivery uses Google Firebase Cloud Messaging.</p>
<h2>Your choices</h2>
<ul><li>Export your data from Settings &rarr; Export my data.</li>
<li>Delete your account and all data from Settings &rarr; Delete account, or see <a href="/delete-account">/delete-account</a>.</li>
<li>Turn notifications off at any time in Settings &rarr; Push notifications.</li></ul>
<h2>Children</h2><p>FitLog is not directed at children under 13.</p>
<h2>Contact</h2><p>${CONTACT}</p>
</body></html>`;

const deletion = `<!doctype html><html><head><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1"><title>Delete your FitLog account</title>${style}</head><body>
<h1>Delete your FitLog account</h1>
<p><b>In the app:</b> open FitLog &rarr; Settings &rarr; <b>Delete account</b> &rarr; confirm. Your account and all workouts are permanently removed from our servers immediately.</p>
<p><b>Without the app:</b> email <a href="mailto:${CONTACT}">${CONTACT}</a> from the address registered to your account with the subject "Delete my FitLog account". We will delete your account and data within 30 days.</p>
<p>What is deleted: your profile, email, workouts, sets, settings and push tokens. Nothing is retained.</p>
</body></html>`;

module.exports = { privacy, deletion };

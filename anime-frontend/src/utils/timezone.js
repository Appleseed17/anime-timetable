export function convertJSTToLocal(dayOfWeek, timeString) {
  if (!dayOfWeek || !timeString) return null;

  const DAYS = ["SUNDAY","MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY"];
  const targetDayIndex = DAYS.indexOf(dayOfWeek.toUpperCase());
  if (targetDayIndex === -1) return null;

  const [hours, minutes] = timeString.split(":").map(Number);

  // Create a Date in JST for the *closest upcoming dayOfWeek* relative to today
  const now = new Date();
  
  // Get current day in JST (add 9 hours)
  const nowJST = new Date(now.getTime() + 9 * 60 * 60 * 1000);
  const currentJSTDay = nowJST.getDay(); // 0=Sun, 6=Sat

  // Calculate how many days to add to get the target day
  let dayDiff = targetDayIndex - currentJSTDay;
  if (dayDiff < 0) dayDiff += 7; // ensure future or same week

  // Create a new JST date for that day
  const broadcastJST = new Date(
    Date.UTC(
      now.getUTCFullYear(),
      now.getUTCMonth(),
      now.getUTCDate() + dayDiff,
      hours - 9, // UTC = JST - 9
      minutes
    )
  );

  // Return JS Date object; browser will treat it as local time
  return broadcastJST;
}
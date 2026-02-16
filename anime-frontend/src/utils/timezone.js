export function convertJSTToLocal(dayOfWeek, timeString) {
  if (!dayOfWeek || !timeString) return null;

  const DAYS = ["SUNDAY","MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY"];
  const targetIndex = DAYS.indexOf(dayOfWeek.toUpperCase());
  if (targetIndex === -1) return null;

  const [hours, minutes] = timeString.split(":").map(Number);

  // Current JST time
  const nowUTC = new Date();
  const nowJST = new Date(nowUTC.getTime() + 9 * 60 * 60 * 1000);

  // Start of JST week (Sunday)
  const startOfWeek = new Date(nowJST);
  startOfWeek.setUTCHours(0, 0, 0, 0);
  startOfWeek.setUTCDate(startOfWeek.getUTCDate() - startOfWeek.getUTCDay());

  // Map day-of-week to date in JST week
  const broadcastJST = new Date(startOfWeek);
  broadcastJST.setUTCDate(startOfWeek.getUTCDate() + targetIndex);
  broadcastJST.setUTCHours(hours - 9, minutes, 0, 0); 

  return broadcastJST;
}



export function convertDateToLocal(date, timeString){
  
  const [year, month, day] = date.split("-").map(Number);
  const [hours, minutes] = timeString.split(":").map(Number);

  const start = new Date(year, month - 1, day, hours - 9, minutes)

  return start




}
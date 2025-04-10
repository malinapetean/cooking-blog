import { useEffect} from "react";

export function CheckingServer(setServerState) {
    useEffect(() => {
        const checkServer = () => {
           fetch("http://localhost:8080/server")
                .then(()=> setServerState(true)) // Server is up
                .catch((error) => {
                    console.error("Error checking server:", error);
                    setServerState(false); // Server is down
                });
        };

        checkServer();
        const interval = setInterval(checkServer, 5000); // Check every 5 seconds
        return () => clearInterval(interval); // Cleanup on unmount
    }, [setServerState]);
}

export function useOnlineStatus(setIsOnline) {
    useEffect(() => {
        const updateOnlineStatus = () => {
            const isOnline = typeof window !== "undefined" && navigator.onLine;
            setIsOnline(isOnline);
        };

        window.addEventListener("online", updateOnlineStatus);
        window.addEventListener("offline", updateOnlineStatus);

        // Initial check
        updateOnlineStatus();

        return () => {
            window.removeEventListener("online", updateOnlineStatus);
            window.removeEventListener("offline", updateOnlineStatus);
        };
    }, [setIsOnline]);
}


